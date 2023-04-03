package com.bookstoreapplication.bookstore.purchase.vo;

import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import java.io.Serializable;

public record PurchaseDetailId(

        @GeneratedValue(strategy = GenerationType.IDENTITY)
        long purchaseDetailId

) implements Serializable { }
