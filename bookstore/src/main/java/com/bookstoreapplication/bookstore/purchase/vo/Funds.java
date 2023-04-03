package com.bookstoreapplication.bookstore.purchase.vo;

import javax.validation.constraints.DecimalMin;
import javax.validation.constraints.NotNull;
import java.io.Serializable;
import java.math.BigDecimal;

public record Funds(

        @DecimalMin(value = "0.0", message = "Minimum value of available funds cannot be less than 0.0")
        @NotNull(message = "Available funds must be not null")
        BigDecimal funds

) implements Serializable { }