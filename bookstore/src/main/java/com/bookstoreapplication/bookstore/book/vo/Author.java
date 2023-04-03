package com.bookstoreapplication.bookstore.book.vo;

import javax.validation.constraints.NotBlank;
import java.io.Serializable;

public record Author(

        @NotBlank(message = "Author must not be blank")
        String author

) implements Serializable { }
