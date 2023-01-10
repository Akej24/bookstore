package com.bookstoreapplication.bookstore.book.exception;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;

import java.time.ZonedDateTime;
import java.util.HashMap;
import java.util.Map;

@ControllerAdvice
public class BookNotFoundExceptionHandler {

    @ResponseBody
    @ExceptionHandler(BookNotFoundException.class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    public ResponseEntity<Object> handleBookNotFoundException(BookNotFoundException exception){

        Map<String, Object> errorMap = new HashMap<>();
        errorMap.put("httpStatus", HttpStatus.NOT_FOUND);
        errorMap.put("exceptionName", BookNotFoundException.class.getSimpleName());
        errorMap.put("errorMessage", exception.getMessage());
        errorMap.put("timestamp", ZonedDateTime.now());

        return new ResponseEntity<>(errorMap, HttpStatus.NOT_FOUND);
    }
}
