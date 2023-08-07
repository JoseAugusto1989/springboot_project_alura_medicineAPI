package com.medicine.course.Medicine.exception;

import jakarta.servlet.http.HttpServletRequest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import java.util.Date;
import java.util.Objects;

@ControllerAdvice
public class RestExceptionHandler {

    @ExceptionHandler(ObjectNotFoundException.class)
    public ResponseEntity<StandardError> handleObjectNotFound(ObjectNotFoundException ex, HttpServletRequest req) {
        StandardError error = new StandardError(
                ex.getMessage(),
                ex.getField(),
                new Date());
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(error);
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<StandardError> handleObjectNotValid(MethodArgumentNotValidException ex) {
        String fieldWithError = Objects.requireNonNull(ex.getBindingResult().getFieldError()).getField();
        String errorMessage = ex.getBindingResult().getFieldError().getDefaultMessage();

        StandardError error = new StandardError(
                errorMessage,
                fieldWithError,
                new Date());
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(error);
    }

    @ExceptionHandler(ValidationException.class)
    public ResponseEntity handleBusinessErrorException(ValidationException ex) {
        return ResponseEntity.badRequest().body(ex.getMessage());
    }
}
