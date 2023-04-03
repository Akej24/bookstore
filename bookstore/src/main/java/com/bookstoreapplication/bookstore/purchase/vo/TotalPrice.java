package com.bookstoreapplication.bookstore.purchase.vo;

import javax.validation.constraints.DecimalMin;
import java.io.Serializable;
import java.math.BigDecimal;

public record TotalPrice(

        @DecimalMin(value = "0.0", message = "The minimum value of total price is 0.0")
        BigDecimal totalPrice

) implements Serializable { }
