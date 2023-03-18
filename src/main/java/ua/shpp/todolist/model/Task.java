package ua.shpp.todolist.model;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import ua.shpp.todolist.utils.Status;

import javax.persistence.*;
import javax.validation.constraints.Future;
import javax.validation.constraints.NotEmpty;
import java.time.LocalDate;


@Entity
@Table
@NoArgsConstructor
@Getter
@Setter
public class Task {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    Long id;
    @NotEmpty
    String action;
    @Future
    LocalDate plannedTime;
    Status status;

    public Task(String action, LocalDate plannedTime, Status status) {
        this.action = action;
        this.plannedTime = plannedTime;
        this.status = status;
    }

    @Override
    public String toString() {
        return "Task{" +
                "id=" + id +
                ", action='" + action + '\'' +
                ", plannedTime=" + plannedTime +
                ", status=" + status +
                '}';
    }
}
