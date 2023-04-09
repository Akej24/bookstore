package com.bookstoreapplication.bookstore.adapters.exception_handler;

import com.bookstoreapplication.bookstore.domain.purchase.exception.PurchaseException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import javax.validation.ConstraintViolation;
import javax.validation.ConstraintViolationException;
import java.util.*;
import java.util.stream.Collectors;

@RestControllerAdvice
class ErrorResponseController {

    @ExceptionHandler(IllegalArgumentException.class)
    ResponseEntity<?> handleIllegalArgumentException(IllegalArgumentException exception){
        String message = exception.getMessage();
        var errors = new ArrayList<ErrorDto>();
        errors.add(ErrorDto.builder().message(message).build());

        ErrorResponse errorResponse = ErrorResponse.builder().errors(errors).build();
        return new ResponseEntity<>(errorResponse, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(ConstraintViolationException.class)
    ResponseEntity<?> handleConstraintViolationException(ConstraintViolationException exception){
        Set<ConstraintViolation<?>> violations = exception.getConstraintViolations();

        List<ErrorDto> errors = violations.stream()
                .map(violation -> new ErrorDto(getFieldName(violation), violation.getMessage()))
                .collect(Collectors.toList());

        ErrorResponse errorResponse = ErrorResponse.builder().errors(errors).build();
        return new ResponseEntity<>(errorResponse, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(PurchaseException.class)
    ResponseEntity<?> handlePurchaseException(PurchaseException exception){
        String message = exception.getMessage();
        var errors = new ArrayList<ErrorDto>();
        errors.add(ErrorDto.builder().message(message).build());

        ErrorResponse errorResponse = ErrorResponse.builder().errors(errors).build();
        return new ResponseEntity<>(errorResponse, HttpStatus.BAD_REQUEST);
    }

    private String getFieldName(ConstraintViolation<?> violation) {
        String[] propertyPath = violation.getPropertyPath().toString().split("\\.");
        return propertyPath[propertyPath.length - 1];
    }

}
