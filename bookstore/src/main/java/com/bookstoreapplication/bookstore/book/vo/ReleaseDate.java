package com.bookstoreapplication.bookstore.book.vo;

import javax.validation.constraints.NotNull;
import java.io.Serializable;
import java.time.LocalDate;

public record ReleaseDate(

        @NotNull(message = "Release date must not be null")
        LocalDate releaseDate

) implements Serializable { }
