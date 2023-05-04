package com.bookstoreapplication.bookstore.exception_handler;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
class ErrorDto {

    @JsonInclude(JsonInclude.Include.NON_NULL)
    private String field;
    private String message;

}
