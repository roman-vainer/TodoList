package ua.shpp.todolist.utils;

import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Component;
import ua.shpp.todolist.model.TaskEntity;
import ua.shpp.todolist.repo.TodoRepository;

import java.time.LocalDate;
import java.util.List;

import static ua.shpp.todolist.dto.Status.*;

@Component
public class WriteToDB {
    @Bean
    CommandLineRunner commandLineRunner(TodoRepository repository) {
        return args -> {
            TaskEntity task1 = new TaskEntity(
                    "Todo any",
                    LocalDate.of(2023, 5, 10),
                    PLANNED);
            TaskEntity task2 = new TaskEntity(
                    "Todo else any",
                    LocalDate.of(2023, 11, 11),
                    PLANNED);

            repository.saveAll(List.of(task1, task2));
        };
    }
}
