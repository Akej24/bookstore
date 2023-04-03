package com.bookstoreapplication.bookstore.purchase.vo;

import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import java.io.Serializable;

public record PurchaseId(

        @GeneratedValue(strategy = GenerationType.IDENTITY)
        long purchaseId

) implements Serializable { }
