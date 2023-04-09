package com.bookstoreapplication.bookstore.domain.book.vo;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;
import java.io.Serializable;

public record NumberOfPages(

        @Min(value = 1, message = "The minimum value of number of pages is 1")
        @NotNull(message = "Number of pages must be not null")
        Integer numberOfPages

) implements Serializable { }
