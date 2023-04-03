package com.bookstoreapplication.bookstore.purchase.vo;

import javax.validation.constraints.NotNull;
import java.io.Serializable;
import java.time.LocalDateTime;

public record PurchaseDate(

        @NotNull(message = "Purchase date must be not null")
        LocalDateTime purchaseDate

) implements Serializable { }
