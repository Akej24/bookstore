package com.bookstoreapplication.bookstore.book.vo;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;
import java.io.Serializable;

public record AvailablePieces(

        @Min(value = 0, message = "The minimum value of available pieces is 0")
        @NotNull(message = "Available pieces must be not null")
        Integer availablePieces

) implements Serializable { }
