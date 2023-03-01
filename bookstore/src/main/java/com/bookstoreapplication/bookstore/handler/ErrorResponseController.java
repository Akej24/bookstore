package com.bookstoreapplication.bookstore.handler;

import jakarta.validation.ConstraintViolation;
import jakarta.validation.ConstraintViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.time.LocalDateTime;
import java.time.ZonedDateTime;
import java.util.*;
import java.util.stream.Collectors;

@RestControllerAdvice
class ErrorResponseController {

    @ExceptionHandler(IllegalArgumentException.class)
    ResponseEntity<?> handleIllegalArgumentException(IllegalArgumentException e){

        Map<String, Object> errorMap = new HashMap<>();
        errorMap.put("errorMessage", e.getMessage());
        errorMap.put("timestamp", ZonedDateTime.now());

        return new ResponseEntity<>(errorMap, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(ConstraintViolationException.class)
    ResponseEntity<?> handleConstraintViolationException(ConstraintViolationException exception){

        Set<ConstraintViolation<?>> violations = exception.getConstraintViolations();

        List<ErrorDto> errors = violations.stream()
                .map(violation -> new ErrorDto(getFieldName(violation), violation.getMessage()))
                .collect(Collectors.toList());

        ErrorResponse errorResponse = new ErrorResponse("FAILED", LocalDateTime.now(), errors);
        return new ResponseEntity<>(errorResponse, HttpStatus.BAD_REQUEST);
    }

    private String getFieldName(ConstraintViolation<?> violation) {
        String[] propertyPath = violation.getPropertyPath().toString().split("\\.");
        return propertyPath[propertyPath.length - 1];
    }

}
