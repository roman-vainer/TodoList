package ua.shpp.todolist.repo;

import org.springframework.data.jpa.repository.JpaRepository;
import ua.shpp.todolist.model.TaskEntity;

public interface TodoRepository extends JpaRepository<TaskEntity, Long> {

}
