package com.bookstoreapplication.bookstore.purchase;

import com.bookstoreapplication.bookstore.book.BookDatabaseModel;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "purchasesDetails")
@Getter
@NoArgsConstructor
@AllArgsConstructor
@Builder(toBuilder = true)
public class PurchaseDetails {

    @EmbeddedId
    PurchaseDetailsKey purchaseDetailsId;

    @ManyToOne
    @MapsId("purchaseId")
    @JoinColumn(name = "purchase_id")
    PurchaseDatabaseModel purchaseDatabaseModel;

    @ManyToOne
    @MapsId("bookId")
    @JoinColumn(name = "book_id")
    BookDatabaseModel bookDatabaseModel;

    @NotNull
    private int booksAmount;
}
