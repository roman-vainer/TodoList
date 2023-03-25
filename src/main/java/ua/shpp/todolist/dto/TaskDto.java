package ua.shpp.todolist.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import ua.shpp.todolist.utils.validator.StatusType;

import javax.validation.constraints.Future;
import javax.validation.constraints.NotEmpty;
import java.time.LocalDate;

import static ua.shpp.todolist.dto.Status.PLANNED;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class TaskDto {
    Long id;
    @NotEmpty(message = "{notEmpty}")
    String action;
    @Future(message = "{future}")
    LocalDate plannedTime;
    @StatusType(value = PLANNED, message = "{statusType}")
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
