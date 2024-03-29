package com.bookstoreapplication.bookstore.exception_handler;

import com.bookstoreapplication.bookstore.auth.exception.AuthenticationException;
import com.bookstoreapplication.bookstore.book.exception.BookException;
import com.bookstoreapplication.bookstore.delivery.exception.DeliveryException;
import com.bookstoreapplication.bookstore.purchase.exception.OrderException;
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
                .map(violation -> new ErrorDto(getConstraintViolationField(violation), violation.getMessage()))
                .collect(Collectors.toList());

        ErrorResponse errorResponse = ErrorResponse.builder().errors(errors).build();
        return new ResponseEntity<>(errorResponse, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler({
            IllegalArgumentException.class,
            OrderException.class,
            UserException.class,
            BookException.class,
            DeliveryException.class,
            AuthenticationException.class,
            JsonDecodeException.class
    })
    ResponseEntity<?> handleBadRequestException(RuntimeException exception){
        List<ErrorDto> errors = List.of(ErrorDto.builder()
                .message((exception instanceof JsonDecodeException)
                        ? substringJsonDecodeExceptionMessage(exception.getMessage())
                        : exception.getMessage())
                .build());
        ErrorResponse errorResponse = ErrorResponse.builder().errors(errors).build();
        return new ResponseEntity<>(errorResponse, HttpStatus.BAD_REQUEST);
    }

    private String substringJsonDecodeExceptionMessage(String message) {
        String pattern = "Exception: ";
        int startIndex = message.indexOf(pattern);
        if(message.contains("expected a ")){
            return "Invalid request";
        }
        return message.substring(startIndex + pattern.length());
    }

    private String getConstraintViolationField(ConstraintViolation<?> violation) {
        String[] propertyPath = violation.getPropertyPath().toString().split("\\.");
        return propertyPath.length > 0 ? propertyPath[propertyPath.length - 1] : "unknown";
    }

}
