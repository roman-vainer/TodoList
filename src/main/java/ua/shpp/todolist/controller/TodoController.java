package ua.shpp.todolist.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import ua.shpp.todolist.model.Task;
import ua.shpp.todolist.services.TodoService;

import javax.validation.Valid;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/todo")
public class TodoController {
    private final TodoService service;

    @Autowired
    public TodoController(TodoService service) {
        this.service = service;
    }

    @GetMapping
    @PreAuthorize("hasAnyRole('ROLE_ADMIN', 'ROLE_MANAGER')")
    public List<Task> getToDoList() {
        return service.getAllTasks();
    }

    @GetMapping("{taskId}")
    public Optional<Task> getOneTask(@PathVariable("taskId") Long taskId) {
        return service.getOneTask(taskId);
    }

    @PostMapping
    @PreAuthorize("hasAuthority('task:planned')")
    public ResponseEntity<String> addTask(@Valid @RequestBody Task task) {
        return service.addTask(task);
    }

    @PutMapping("{taskId}")
    @PreAuthorize("hasAuthority('task:progress')")
    public ResponseEntity<String> editTask(@PathVariable("taskId") Long taskId, @RequestBody Task task) {
        return service.taskStatusChange(taskId, task);
    }

    @DeleteMapping("{taskId}")
    @PreAuthorize("hasAuthority('task:cancelled')")
    public ResponseEntity<String> deleteTask(@PathVariable("taskId") Long taskId) {
        return service.deleteTask(taskId);
    }
}
