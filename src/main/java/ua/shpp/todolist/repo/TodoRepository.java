package ua.shpp.todolist.repo;

import org.springframework.data.jpa.repository.JpaRepository;
import ua.shpp.todolist.model.Task;

import java.util.Optional;

public interface TodoRepository extends JpaRepository <Task, Long> {

    Optional<Task> findById (Long id);
}
