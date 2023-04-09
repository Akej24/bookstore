package com.bookstoreapplication.bookstore.domain.purchase.value_objects;

import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import java.io.Serializable;

public record PurchaseDetailId(

        @GeneratedValue(strategy = GenerationType.IDENTITY)
        long purchaseDetailId

) implements Serializable { }
