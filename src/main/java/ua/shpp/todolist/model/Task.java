package ua.shpp.todolist.model;

import lombok.AllArgsConstructor;
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
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class Task {
    @Id
    //@GeneratedValue(strategy = GenerationType.AUTO)
    Long id;
    /*@NotEmpty
    @NotNull
    Employee employee;*/
    @NotEmpty
    String action;
    @Future
    LocalDate plannedTime;
    Status status;

    @Override
    public String toString() {
        return "Task{" +
                "id=" + id +
//                ", employee=" + employee +
                ", action='" + action + '\'' +
                ", plannedTime=" + plannedTime +
                ", status=" + status +
                '}';
    }
}
