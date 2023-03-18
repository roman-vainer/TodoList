package ua.shpp.todolist.config;

import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import ua.shpp.todolist.model.Task;
import ua.shpp.todolist.repo.TodoRepository;
import ua.shpp.todolist.utils.Status;

import java.time.LocalDate;
import java.util.List;

@Configuration
public class Config {

    @Bean
    CommandLineRunner commandLineRunner(TodoRepository repository) {
        return args -> {
            Task task1 = new Task (
                    1L,
//                    Employee.EMPLOYEE,
                    "Todo any",
                    LocalDate.of(2023, 05, 05),
                    Status.PLANNED);
            Task task2 = new Task (
                    2L,
//                    Employee.ADMIN,
                    "Todo admin",
                    LocalDate.of(2023, 11, 11),
                    Status.PLANNED);

            repository.saveAll(List.of(task1, task2));
        };
    }
}
