package com.bookstoreapplication.bookstore.domain.purchase.value_objects;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;
import java.io.Serializable;

public record BooksAmount(

        @Min(value = 1, message = "The minimum value of books amount is 1")
        @NotNull(message = "Books amount cannot be null")
        Integer booksAmount

) implements Serializable { }
