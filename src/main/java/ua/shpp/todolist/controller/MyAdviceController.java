package ua.shpp.todolist.controller;

import org.springframework.context.support.DefaultMessageSourceResolvable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import ua.shpp.todolist.dto.ErrorDto;

import java.time.LocalDateTime;
import java.util.stream.Collectors;

@ControllerAdvice
public class MyAdviceController {

    @ExceptionHandler(MethodArgumentNotValidException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ResponseBody
    public ResponseEntity<ErrorDto> processValidationError(MethodArgumentNotValidException e) {

        String errorMessage = e.getBindingResult().getAllErrors().stream()
                .map(DefaultMessageSourceResolvable::getDefaultMessage)
                .collect(Collectors.toList()).toString();

        return ResponseEntity.badRequest().body(new ErrorDto(LocalDateTime.now(),
                HttpStatus.BAD_REQUEST.value(),
                "Validation error",
                errorMessage));
    }

    @ExceptionHandler(IllegalStateException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ResponseBody
    public ResponseEntity<ErrorDto> processIllegalStateError(IllegalStateException e) {

        return ResponseEntity.badRequest().body(new ErrorDto(
                LocalDateTime.now(),
                HttpStatus.BAD_REQUEST.value(),
                "incorrect request",
                e.getMessage()));
    }
}
