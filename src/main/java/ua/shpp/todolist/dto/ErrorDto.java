package ua.shpp.todolist.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.ToString;

import java.time.LocalDateTime;

@AllArgsConstructor
@Getter
@ToString
public class ErrorDto {
    private LocalDateTime time;
    private int status;
    private String error;
    private String message;
}
