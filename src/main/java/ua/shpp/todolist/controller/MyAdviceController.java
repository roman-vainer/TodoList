package ua.shpp.todolist.controller;

import com.fasterxml.jackson.databind.exc.InvalidFormatException;
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

        String errorMessage =  e.getBindingResult().getAllErrors().stream()
                .map(DefaultMessageSourceResolvable::getDefaultMessage)
                .collect(Collectors.toList()).toString();

        ErrorDto errorDto = new ErrorDto(LocalDateTime.now(),
                HttpStatus.BAD_REQUEST.value(),
                "Validation error",
                errorMessage);

        return ResponseEntity.badRequest().body(errorDto);
    }

    @ExceptionHandler(InvalidFormatException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ResponseBody
    public ResponseEntity<ErrorDto> statusError(InvalidFormatException e) {

        ErrorDto errorDto = new ErrorDto(LocalDateTime.now(),
                HttpStatus.BAD_REQUEST.value(),
                "Status format of task error",
                "Status must be one of the values: [CANCELLED, DONE, PLANNED, PROGRESS]");

        return ResponseEntity.badRequest().body(errorDto);
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
