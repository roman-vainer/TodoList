package ua.shpp.todolist.services;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ua.shpp.todolist.dto.TaskDto;
import ua.shpp.todolist.model.TaskEntity;
import ua.shpp.todolist.repo.TodoRepository;
import ua.shpp.todolist.utils.Status;

import javax.validation.Valid;
import java.util.List;
import java.util.Locale;
import java.util.ResourceBundle;
import java.util.stream.Collectors;

@Service
@Slf4j
public class TodoService {
    private final TodoRepository repository;
    private static final String PATH_MESSAGES = "messages";

    @Autowired
    public TodoService(TodoRepository repository) {
        this.repository = repository;
    }

    public ResponseEntity<String> addTask(@Valid TaskDto taskDto, Locale locale) {
        ResourceBundle bundle = ResourceBundle.getBundle(PATH_MESSAGES, locale);

        if (taskDto.getId() != null) {
            throw new IllegalStateException(bundle.getString("idGenerated"));
        }
        log.info("===Add Task===");
        TaskEntity taskEntity = DtoProjection.dtoToEntity(taskDto);
        repository.save(taskEntity);
        return ResponseEntity.status(HttpStatus.CREATED).body("Added task: " + taskEntity);

    }

    public List<TaskDto> getAllTasks() {
        log.info("===get All Task===");
        return repository.findAll().stream().map(DtoProjection::entityToDto).collect(Collectors.toList());
    }

    public TaskDto getOneTask(Long taskId, Locale locale) {
        log.info("get One Task");
        ResourceBundle bundle = ResourceBundle.getBundle(PATH_MESSAGES, locale);
        isTaskIdExist(taskId, bundle);

        return DtoProjection.entityToDto(repository.getReferenceById(taskId));
    }

    @Transactional
    public ResponseEntity<String> taskStatusChange(Long taskId, TaskDto taskDto, Locale locale) {
        ResourceBundle bundle = ResourceBundle.getBundle(PATH_MESSAGES, locale);

        isTaskIdExist(taskId, bundle);
        TaskEntity changingTask = repository.getReferenceById(taskId);

        if (isNewStatusCorrect(changingTask.getStatus(), taskDto.getStatus(), bundle)) {
            changingTask.setStatus(taskDto.getStatus());

            return ResponseEntity.status(HttpStatus.OK).body("Changed task " + taskId + ": "
                    + DtoProjection.entityToDto(changingTask));
        } else {
            throw new IllegalStateException(bundle.getString("incorrectChangeStatus"));
        }
    }

    private boolean isNewStatusCorrect(Status currentStatus, Status newStatus, ResourceBundle bundle) {
        if (currentStatus == Status.DONE || currentStatus == Status.CANCELLED) {
            throw new IllegalStateException(bundle.getString("finalStatus"));
        }
        return currentStatus.getAllowedState().contains(newStatus);
    }

    public ResponseEntity<String> deleteTask(Long taskId, Locale locale) {
        ResourceBundle bundle = ResourceBundle.getBundle(PATH_MESSAGES, locale);

        isTaskIdExist(taskId, bundle);
        repository.deleteById(taskId);
        return ResponseEntity.status(HttpStatus.OK).body("Delete task " + taskId);
    }

    private void isTaskIdExist(Long taskId, ResourceBundle bundle) {
        if (!repository.existsById(taskId)) {
            throw new IllegalStateException(bundle.getString("taskNotExist") + taskId);
        }
    }
}
