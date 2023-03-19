package ua.shpp.todolist.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import ua.shpp.todolist.utils.Status;
import ua.shpp.todolist.validator.StatusTypeSubset;

import javax.validation.constraints.Future;
import javax.validation.constraints.NotEmpty;
import java.time.LocalDate;

import static ua.shpp.todolist.utils.Status.PLANNED;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class TaskDto {
    Long id;
    @NotEmpty(message = "Action must not be empty")
    String action;
    @Future(message = "Date must be in future")
    LocalDate plannedTime;
    @StatusTypeSubset(anyOf = {PLANNED}, message = "New task must have a status PLANNED")
    Status status;

    @Override
    public String toString() {
        return "TaskDto{" +
                "id=" + id +
                ", action='" + action + '\'' +
                ", plannedTime=" + plannedTime +
                ", status=" + status +
                '}';
    }
}
