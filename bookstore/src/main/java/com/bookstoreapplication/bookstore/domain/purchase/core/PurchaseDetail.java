package com.bookstoreapplication.bookstore.domain.purchase;

import com.bookstoreapplication.bookstore.domain.purchase.value_objects.BooksAmount;
import com.bookstoreapplication.bookstore.domain.purchase.value_objects.PurchaseDetailId;
import com.bookstoreapplication.bookstore.domain.purchase.value_objects.SimpleBookId;
import com.bookstoreapplication.bookstore.domain.purchase.value_objects.SimplePurchaseId;
import com.bookstoreapplication.bookstore.purchase.value_objects.*;
import lombok.*;

import javax.persistence.*;
import java.io.Serializable;

@Entity
@Table(name = "purchase_details")
@Getter
@Setter
@NoArgsConstructor
public class PurchaseDetail implements Serializable {

    @Id
    private PurchaseDetailId purchaseDetailId;
    private SimpleBookId bookId;
    private BooksAmount booksAmount;
    private SimplePurchaseId purchaseId;

    public PurchaseDetail(SimpleBookId bookId, BooksAmount booksAmount, SimplePurchaseId purchaseId) {
        this.bookId = bookId;
        this.booksAmount = booksAmount;
        this.purchaseId = purchaseId;
    }
}
