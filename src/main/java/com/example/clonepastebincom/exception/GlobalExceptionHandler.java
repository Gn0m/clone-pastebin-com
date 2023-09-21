package com.example.clonepastebincom.exception;

import jakarta.validation.ConstraintViolation;
import jakarta.validation.ConstraintViolationException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import java.util.Optional;

@ControllerAdvice
@Slf4j
public class GlobalExceptionHandler {

    @ExceptionHandler
    public ResponseEntity<Error> catchResourceNotFoundException(ResourceNotFoundException e) {
        log.error(e.getMessage(), e);
        return new ResponseEntity<>(new Error(HttpStatus.NOT_FOUND.value(), e.getMessage()), HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler
    public ResponseEntity<Error> catchExpireDateException(ExpireDateException e) {
        log.error(e.getMessage(), e);
        return new ResponseEntity<>(new Error(HttpStatus.NOT_FOUND.value(), e.getMessage()), HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler
    public ResponseEntity<Error> catchConstraintViolationException(ConstraintViolationException e) {
        log.error(e.getMessage(), e);

        String message = getConstraintViolationMessage(e);

        return new ResponseEntity<>(new Error(HttpStatus.BAD_REQUEST.value(), message), HttpStatus.BAD_REQUEST);
    }

    private String getConstraintViolationMessage(ConstraintViolationException e) {
        Optional<String> first = e.getConstraintViolations()
                .stream().map(ConstraintViolation::getMessageTemplate)
                .findFirst();
        String message = null;

        if (first.isPresent()) {
            message = first.get();
        }
        return message;
    }
}
