package ua.shpp.todolist.services;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ua.shpp.todolist.dto.TaskDto;
import ua.shpp.todolist.model.TaskEntity;
import ua.shpp.todolist.repo.TodoRepository;
import ua.shpp.todolist.utils.Status;

import javax.validation.Valid;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

@Service
@Slf4j
public class TodoService {
    private final TodoRepository repository;

    @Autowired
    public TodoService(TodoRepository repository) {
        this.repository = repository;
    }

    public ResponseEntity<String> addTask(@Valid TaskDto taskDto) {
        if (taskDto.getId() != null) {
            throw new IllegalStateException("ID should be generated automatically");
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

    public TaskDto getOneTask(Long taskId) {
        log.info("get One Task");
        isTaskIdExist(taskId);

        return DtoProjection.entityToDto(repository.getReferenceById(taskId));
    }

    @Transactional
    public ResponseEntity<String> taskStatusChange(Long taskId, TaskDto taskDto) {
        TaskEntity changingTask = repository.getReferenceById(taskId)/*findById(taskId)*/;
//                .orElseThrow(() -> new IllegalStateException("task with id " + taskId + " does not exist"));

        if (isNewStatusCorrect(changingTask.getStatus(), taskDto.getStatus())) {
            changingTask.setStatus(taskDto.getStatus());

            return ResponseEntity.status(HttpStatus.OK).body("Changed task " + taskId + ": "
                    + DtoProjection.entityToDto(changingTask));
        } else {
            throw new IllegalStateException("Status change is not correct");
        }
    }

    private boolean isNewStatusCorrect(Status currentStatus, Status newStatus) {
        if (currentStatus == Status.DONE || currentStatus == Status.CANCELLED) {
            throw new IllegalStateException("Task status cannot be changed because it is completed or cancelled");
        }
        return currentStatus.getAllowedState().contains(newStatus);
    }

    public ResponseEntity<String> deleteTask(Long taskId) {
        isTaskIdExist(taskId);
        repository.deleteById(taskId);
        return ResponseEntity.status(HttpStatus.OK).body("Delete task " + taskId);
    }

    private void isTaskIdExist(Long taskId) {
        if (!repository.existsById(taskId)) {
            throw new IllegalStateException("task with id " + taskId + " does not exist");
        }
    }
}
