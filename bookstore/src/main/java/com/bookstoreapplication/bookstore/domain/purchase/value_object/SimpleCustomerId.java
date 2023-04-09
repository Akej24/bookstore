package com.bookstoreapplication.bookstore.domain.purchase.value_objects;

import javax.validation.constraints.NotNull;
import java.io.Serializable;

public record SimpleCustomerId(

        @NotNull(message = "Customer id cannot be null")
        long customerId

) implements Serializable { }
