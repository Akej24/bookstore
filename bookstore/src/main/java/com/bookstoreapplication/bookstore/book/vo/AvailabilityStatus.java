package com.bookstoreapplication.bookstore.book.vo;

import javax.validation.constraints.NotNull;
import java.io.Serializable;

public record AvailabilityStatus(

        @NotNull(message = "Status mut not be null")
        Boolean status

) implements Serializable { }
