package com.bookstoreapplication.bookstore.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.time.ZonedDateTime;
import java.util.HashMap;
import java.util.Map;

@RestControllerAdvice
class GlobalControllerAdvice {
////.
    @ExceptionHandler(IllegalArgumentException.class)
    ResponseEntity<?> handleIllegalArgumentException(IllegalArgumentException e){

        Map<String, Object> errorMap = new HashMap<>();
        errorMap.put("errorMessage", e.getMessage());
        errorMap.put("timestamp", ZonedDateTime.now());

        return new ResponseEntity<>(errorMap, HttpStatus.BAD_REQUEST);
    }

}
