package com.bookstoreapplication.bookstore.user.login.exception;

import com.bookstoreapplication.bookstore.user.registration.exception.InvalidInputException;
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
public class UserNotFoundExceptionHandler {

    @ResponseBody
    @ExceptionHandler(UserNotFoundException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ResponseEntity<Object> handleInvalidInputException(UserNotFoundException exception){

        Map<String, Object> errorMap = new HashMap<>();
        errorMap.put("httpStatus", HttpStatus.BAD_REQUEST);
        errorMap.put("exceptionName", UserNotFoundException.class.getSimpleName());
        errorMap.put("errorMessage", exception.getMessage());
        errorMap.put("timestamp", ZonedDateTime.now());

        return new ResponseEntity<>(errorMap, HttpStatus.BAD_REQUEST);
    }

}
