package com.bookstoreapplication.bookstore.purchase;

import com.bookstoreapplication.bookstore.book.BookDatabaseModel;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.io.Serializable;

@Entity
@Table(name = "purchasesDetails")
@Getter
@NoArgsConstructor
@AllArgsConstructor
@Builder(toBuilder = true)
@IdClass(PurchaseDetailsKey.class)
public class PurchaseDetails implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long purchaseDetailsId;

    @Id
    @ManyToOne
    @JoinColumn(name = "purchase_id")
    private PurchaseDatabaseModel purchaseDatabaseModel;

    @Id
    @ManyToOne
    @JoinColumn(name = "book_id")
    private BookDatabaseModel bookDatabaseModel;

    @NotNull
    private int booksAmount;
}
