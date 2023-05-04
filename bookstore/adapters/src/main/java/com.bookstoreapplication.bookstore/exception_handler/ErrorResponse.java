package com.bookstoreapplication.bookstore.exception_handler;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.List;

@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
class ErrorResponse {

    @Builder.Default
    private String status = "FAILED";

    @Builder.Default
    private LocalDateTime timestamp = LocalDateTime.now();

    private List<ErrorDto> errors;

}
