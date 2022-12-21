package com.bookstoreapplication.bookstore.purchase;

import com.bookstoreapplication.bookstore.book.Book;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;

@Entity
@Table(name = "purchasesBooks")
@IdClass(PurchaseBookId.class)
public class PurchaseBook {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long purchaseBookId;

    @Id
    @ManyToOne
    @JoinColumn(name = "purchase_id")
    Purchase purchaseId;

    @Id
    @ManyToOne
    @JoinColumn(name = "book_id")
    Book bookId;

    @NotNull
    private int booksAmount;
}
