package ua.shpp.todolist.services;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ua.shpp.todolist.model.Task;
import ua.shpp.todolist.repo.TodoRepository;
import ua.shpp.todolist.utils.Status;

import java.util.List;
import java.util.Optional;

@Service
@Slf4j
public class TodoService {
    private final TodoRepository repository;

    @Autowired
    public TodoService(TodoRepository repository) {
        this.repository = repository;
    }

    public ResponseEntity<String> addTask(Task task) {
        if(task.getStatus() != Status.PLANNED) {
            throw new IllegalStateException("New task must have a status PLANNED");
        }
        log.info("Add Task");
        repository.save(task);
        return ResponseEntity.status(HttpStatus.CREATED).body("Added task: " + task);
    }

    public List<Task> getAllTasks() {
        log.info("get All Task");
        return repository.findAll();
    }

    public Optional<Task> getOneTask(Long taskId) {
        log.info("get One Task");
        return repository.findById(taskId);
    }

    @Transactional
    public ResponseEntity<String> editTask(Long taskId, Task task) {
        Task changingTask = repository.findById(taskId)
                .orElseThrow(() -> new IllegalStateException("task with id " + taskId + " does not exist"));

        if (task.getPlannedTime() != null) {
            changingTask.setPlannedTime(task.getPlannedTime());
        }
        if (task.getAction() != null) {
            changingTask.setAction(task.getAction());
        }
        if (task.getStatus() != null) {
            changingTask.setStatus(task.getStatus());
        }
        /*if (task.getEmployee() != null) {
            changingTask.setEmployee(task.getEmployee());
        }*/
        return ResponseEntity.status(HttpStatus.OK).body("Changed task " + taskId + ": " + changingTask);
    }
}
