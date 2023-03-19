package ua.shpp.todolist.dto;

import ua.shpp.todolist.utils.Status;

import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.validation.constraints.Future;
import javax.validation.constraints.NotEmpty;
import java.time.LocalDate;

public class TaskDto {

    @NotEmpty
    String action;
    @Future
    LocalDate plannedTime;
    Status status;
}
