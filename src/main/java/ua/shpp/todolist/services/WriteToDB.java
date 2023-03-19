package ua.shpp.todolist.services;

import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Component;
import ua.shpp.todolist.model.TaskEntity;
import ua.shpp.todolist.repo.TodoRepository;
import ua.shpp.todolist.utils.Status;

import java.time.LocalDate;
import java.util.List;

@Component
public class WriteToDB {
    @Bean
    CommandLineRunner commandLineRunner(TodoRepository repository) {
        return args -> {
            TaskEntity task1 = new TaskEntity(
                    "Todo any",
                    LocalDate.of(2023, 05, 05),
                    Status.PLANNED);
            TaskEntity task2 = new TaskEntity(
                    "Todo else any",
                    LocalDate.of(2023, 11, 11),
                    Status.PLANNED);

            repository.saveAll(List.of(task1, task2));
        };
    }
}
