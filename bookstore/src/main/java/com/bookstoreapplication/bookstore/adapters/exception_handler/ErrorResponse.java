package com.bookstoreapplication.bookstore.adapters.exception_handler;

import lombok.*;

import java.time.LocalDateTime;
import java.util.List;

@Data
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
