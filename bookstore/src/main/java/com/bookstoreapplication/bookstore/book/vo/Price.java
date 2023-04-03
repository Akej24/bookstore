package com.bookstoreapplication.bookstore.book.vo;

import javax.validation.constraints.DecimalMin;
import javax.validation.constraints.NotNull;
import java.io.Serializable;
import java.math.BigDecimal;

public record Price(

        @DecimalMin(value = "0.0", message = "The minimum value of the price is 0.0")
        @NotNull(message = "Price must be not null")
        BigDecimal price

) implements Serializable { }
