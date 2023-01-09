package com.bookstoreapplication.bookstore.user.registration.exception;

import com.bookstoreapplication.bookstore.user.registration.exception.InvalidInputException;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;

import java.time.ZonedDateTime;
import java.util.HashMap;
import java.util.Map;

@ControllerAdvice
public class InvalidInputExceptionHandler {

    @ResponseBody
    @ExceptionHandler(InvalidInputException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public Map<String, Object> exceptionHandler(InvalidInputException exception){

        Map<String, Object> errorMap=new HashMap<>();
        errorMap.put("httpStatus", HttpStatus.BAD_REQUEST);
        errorMap.put("exceptionName", InvalidInputException.class.getSimpleName());
        errorMap.put("errorMessage", exception.getMessage());
        errorMap.put("timestamp", ZonedDateTime.now());

        return errorMap;
    }

}
