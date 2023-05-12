package com.bookstoreapplication.bookstore.exception_handler;

import com.bookstoreapplication.bookstore.book.exception.BookException;
import com.bookstoreapplication.bookstore.order.exception.OrderException;
import com.bookstoreapplication.bookstore.user.exception.UserException;
import dev.mccue.json.JsonDecodeException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import javax.validation.ConstraintViolation;
import javax.validation.ConstraintViolationException;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@RestControllerAdvice
class ErrorResponseController {

    @ExceptionHandler(ConstraintViolationException.class)
    ResponseEntity<?> handleConstraintViolationException(ConstraintViolationException exception){
        Set<ConstraintViolation<?>> violations = exception.getConstraintViolations();

        List<ErrorDto> errors = violations.stream()
                .map(violation -> new ErrorDto(getFieldName(violation), violation.getMessage()))
                .collect(Collectors.toList());

        ErrorResponse errorResponse = ErrorResponse.builder().errors(errors).build();
        return new ResponseEntity<>(errorResponse, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler({
            IllegalArgumentException.class,
            OrderException.class,
            UserException.class,
            BookException.class,
            JsonDecodeException.class
    })
    ResponseEntity<?> handleIllegalArgumentException(RuntimeException exception){
        List<ErrorDto> errors = List.of(ErrorDto.builder()
                .message((exception instanceof JsonDecodeException) ? "invalid json" : exception.getMessage())
                .build());
        ErrorResponse errorResponse = ErrorResponse.builder().errors(errors).build();
        return new ResponseEntity<>(errorResponse, HttpStatus.BAD_REQUEST);
    }

    private String getFieldName(ConstraintViolation<?> violation) {
        String[] propertyPath = violation.getPropertyPath().toString().split("\\.");
        return propertyPath.length > 0 ? propertyPath[propertyPath.length - 1] : "unknown";
    }

}
