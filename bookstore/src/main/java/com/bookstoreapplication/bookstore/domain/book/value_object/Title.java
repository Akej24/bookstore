package com.bookstoreapplication.bookstore.domain.book.vo;

import javax.validation.constraints.NotBlank;
import java.io.Serializable;

public record Title(

        @NotBlank(message = "Title must not be blank")
        String title

) implements Serializable { }
