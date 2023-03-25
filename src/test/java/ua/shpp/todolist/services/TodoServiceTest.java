package ua.shpp.todolist.services;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import ua.shpp.todolist.dto.TaskDto;
import ua.shpp.todolist.model.TaskEntity;
import ua.shpp.todolist.repo.TodoRepository;
import ua.shpp.todolist.utils.DtoProjection;

import java.time.LocalDate;
import java.util.Locale;

import static org.junit.jupiter.api.Assertions.*;
import static ua.shpp.todolist.dto.Status.*;

class TodoServiceTest {
    TodoRepository repository = Mockito.mock(TodoRepository.class);
    TodoService service = new TodoService(repository);
    Locale locale = new Locale("en");
    TaskEntity currentTask;
    TaskDto newStatusTask;

    @BeforeEach
    void setUp() {
        currentTask = DtoProjection.dtoToEntity(new TaskDto(
                1L,
                "todo",
                LocalDate.of(2023, 11, 11),
                PLANNED));

        newStatusTask = new TaskDto(
                2L,
                "todo",
                LocalDate.of(2023, 11, 11),
                PLANNED);

        Mockito.when(repository.getReferenceById(1L)).thenReturn(currentTask);
        Mockito.when(repository.existsById(1L)).thenReturn(true);
    }

    @Test()
    void status_Planned_ToNextLevelCorrectChange() {
        currentTask.setStatus(PLANNED);
        newStatusTask.setStatus(PROGRESS);

        service.taskStatusChange(1L, newStatusTask, locale);
        assertEquals(currentTask.getStatus(), newStatusTask.getStatus());
    }

    @Test()
    void status_Planned_IncorrectChangeTest() throws IllegalStateException {
        currentTask.setStatus(PLANNED);
        newStatusTask.setStatus(DONE);

        Throwable exception = assertThrows(IllegalStateException.class, () -> {
            service.taskStatusChange(1L, newStatusTask, locale);
        });
        assertEquals("Status change is not correct", exception.getMessage());
    }

    @Test()
    void status_Planned_ToCancelledChange() {
        currentTask.setStatus(PLANNED);
        newStatusTask.setStatus(CANCELLED);

        service.taskStatusChange(1L, newStatusTask, locale);
        assertEquals(currentTask.getStatus(), newStatusTask.getStatus());
    }

    @Test()
    void status_Progress_ToNextLevelCorrectChange() {
        currentTask.setStatus(PROGRESS);
        newStatusTask.setStatus(SIGNED);

        service.taskStatusChange(1L, newStatusTask, locale);
        assertEquals(currentTask.getStatus(), newStatusTask.getStatus());
    }

    @Test()
    void status_Progress_AtTheSameLevelCorrectChange() {
        currentTask.setStatus(PROGRESS);
        newStatusTask.setStatus(POSTPONED);

        service.taskStatusChange(1L, newStatusTask, locale);
        assertEquals(currentTask.getStatus(), newStatusTask.getStatus());
    }

    @Test()
    void status_Progress_IncorrectChange() throws IllegalStateException {
        currentTask.setStatus(PROGRESS);
        newStatusTask.setStatus(DONE);

        Throwable exception = assertThrows(IllegalStateException.class, () -> {
            service.taskStatusChange(1L, newStatusTask, locale);
        });
        assertEquals("Status change is not correct", exception.getMessage());
    }

    @Test()
    void status_Progress_ToCancelledChange() {
        currentTask.setStatus(PROGRESS);
        newStatusTask.setStatus(CANCELLED);

        service.taskStatusChange(1L, newStatusTask, locale);
        assertEquals(currentTask.getStatus(), newStatusTask.getStatus());
    }

    @Test()
    void status_Notified_ToNextLevelCorrectChange() {
        currentTask.setStatus(NOTIFIED);
        newStatusTask.setStatus(DONE);

        service.taskStatusChange(1L, newStatusTask, locale);
        assertEquals(currentTask.getStatus(), newStatusTask.getStatus());
    }

    @Test()
    void status_Notified_AtTheSameLevelCorrectChange() {
        currentTask.setStatus(NOTIFIED);
        newStatusTask.setStatus(SIGNED);

        service.taskStatusChange(1L, newStatusTask, locale);
        assertEquals(currentTask.getStatus(), newStatusTask.getStatus());
    }

    @Test()
    void status_Notified_IncorrectChange() throws IllegalStateException {
        currentTask.setStatus(NOTIFIED);
        newStatusTask.setStatus(POSTPONED);

        Throwable exception = assertThrows(IllegalStateException.class, () -> {
            service.taskStatusChange(1L, newStatusTask, locale);
        });
        assertEquals("Status change is not correct", exception.getMessage());
    }

    @Test()
    void status_Notified_ToCancelledChange() {
        currentTask.setStatus(NOTIFIED);
        newStatusTask.setStatus(CANCELLED);

        service.taskStatusChange(1L, newStatusTask, locale);
        assertEquals(currentTask.getStatus(), newStatusTask.getStatus());
    }

    @Test()
    void immutableStatus_Done_Change() throws IllegalStateException {
        currentTask.setStatus(DONE);
        newStatusTask.setStatus(PROGRESS);

        Throwable exception = assertThrows(IllegalStateException.class, () -> {
            service.taskStatusChange(1L, newStatusTask, locale);
        });
        assertEquals("Task status cannot be changed because it is completed or cancelled", exception.getMessage());
    }

    @Test()
    void immutableStatus_Cancelled_Change() throws IllegalStateException {
        currentTask.setStatus(CANCELLED);
        newStatusTask.setStatus(NOTIFIED);

        Throwable exception = assertThrows(IllegalStateException.class, () -> {
            service.taskStatusChange(1L, newStatusTask, locale);
        });
        assertEquals("Task status cannot be changed because it is completed or cancelled", exception.getMessage());
    }
}