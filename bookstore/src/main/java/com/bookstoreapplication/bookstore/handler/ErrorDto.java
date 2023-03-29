package com.bookstoreapplication.bookstore.handler;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.*;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
class ErrorDto {

    @JsonInclude(JsonInclude.Include.NON_NULL)
    private String field;
    private String message;

}
