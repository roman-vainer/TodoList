package ua.shpp.todolist.repo;

import org.springframework.data.jpa.repository.JpaRepository;
import ua.shpp.todolist.model.TaskEntity;

import java.util.Optional;

public interface TodoRepository extends JpaRepository <TaskEntity, Long> {

    Optional<TaskEntity> findById (Long id);
}
