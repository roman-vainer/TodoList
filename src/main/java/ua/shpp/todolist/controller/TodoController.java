package ua.shpp.todolist.controller;

import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import ua.shpp.todolist.dto.TaskDto;
import ua.shpp.todolist.services.TodoService;

import javax.validation.Valid;
import java.util.List;
import java.util.Locale;

@RestController
@RequestMapping("/todo")
@SecurityRequirement(name = "Admin-User-Manager")
public class TodoController {
    private final TodoService service;

    @Autowired
    public TodoController(TodoService service) {
        this.service = service;
    }

    @GetMapping
    @PreAuthorize("hasAnyRole('ROLE_ADMIN', 'ROLE_MANAGER')")
    public List<TaskDto> getToDoList() {
        return service.getAllTasks();
    }

    @GetMapping("{taskId}")
    public TaskDto getOneTask(@PathVariable("taskId") Long taskId,
                              @RequestParam Locale locale) {
        return service.getOneTask(taskId, locale);
    }

    @PostMapping
    @PreAuthorize("hasAuthority('task:planned')")
    public ResponseEntity<String> addTask(@Valid @RequestBody TaskDto task,
                                          @RequestParam Locale locale) {
        return service.addTask(task, locale);
    }

    @PutMapping("{taskId}")
    @PreAuthorize("hasAuthority('task:progress')")
    public ResponseEntity<String> editTaskStatus(@PathVariable("taskId") Long taskId,
                                                 @RequestBody TaskDto task,
                                                 @RequestParam Locale locale) {
        return service.taskStatusChange(taskId, task, locale);
    }

    @DeleteMapping("{taskId}")
    @PreAuthorize("hasAuthority('task:deleted')")
    public ResponseEntity<String> deleteTask(@PathVariable("taskId") Long taskId,
                                             @RequestParam Locale locale) {
        return service.deleteTask(taskId, locale);
    }
}
