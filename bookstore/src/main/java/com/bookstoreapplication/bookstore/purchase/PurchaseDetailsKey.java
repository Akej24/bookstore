package com.bookstoreapplication.bookstore.purchase;

import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;
import lombok.EqualsAndHashCode;

import java.io.Serializable;

@Embeddable
@EqualsAndHashCode
public class PurchaseDetailsKey implements Serializable {

    @Column(name = "purchase_id")
    long purchaseId;
    @Column(name = "book_id")
    long bookId;

}
