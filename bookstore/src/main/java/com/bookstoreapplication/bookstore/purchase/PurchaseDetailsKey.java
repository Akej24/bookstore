package com.bookstoreapplication.bookstore.purchase;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.io.Serializable;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class PurchaseDetailsKey implements Serializable {

    private long purchaseDetailsId;
    @Column(name = "purchase_id")
    private long purchaseDatabaseModel;
    @Column(name = "book_id")
    private long bookDatabaseModel;

}
