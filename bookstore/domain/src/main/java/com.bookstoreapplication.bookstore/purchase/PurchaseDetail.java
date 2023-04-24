package com.bookstoreapplication.bookstore.purchase;

import com.bookstoreapplication.bookstore.purchase.value_object.BooksAmount;
import com.bookstoreapplication.bookstore.purchase.value_object.PurchaseDetailId;
import com.bookstoreapplication.bookstore.purchase.value_object.SimpleBookId;
import com.bookstoreapplication.bookstore.purchase.value_object.SimplePurchaseId;
import lombok.*;

import javax.persistence.*;
import java.io.Serializable;

@Table(name="purchase_details")
@Entity
@Getter
@AllArgsConstructor(access = AccessLevel.PROTECTED)
@NoArgsConstructor
class PurchaseDetail implements Serializable {

    @EmbeddedId
    private PurchaseDetailId purchaseDetailId;
    @Embedded
    private SimpleBookId bookId;
    @Embedded
    private BooksAmount booksAmount;
    @Embedded
    private SimplePurchaseId purchaseId;

    PurchaseDetail(SimpleBookId bookId, BooksAmount booksAmount, SimplePurchaseId purchaseId) {
        this.bookId = bookId;
        this.booksAmount = booksAmount;
        this.purchaseId = purchaseId;
    }
}
