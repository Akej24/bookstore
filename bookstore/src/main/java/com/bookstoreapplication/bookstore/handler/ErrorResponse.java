package com.bookstoreapplication.bookstore.handler;

import lombok.*;

import java.time.LocalDateTime;
import java.util.List;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ErrorResponse {

    @Builder.Default
    private String status = "FAILED";

    @Builder.Default
    private LocalDateTime timestamp = LocalDateTime.now();

    private List<ErrorDto> errors;

}
