package com.bookstoreapplication.bookstore.book;

import com.bookstoreapplication.bookstore.purchase.Purchase;
import com.bookstoreapplication.bookstore.purchase.PurchaseBook;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.RequiredArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Set;

@Entity
@Table(name = "books")
@Getter
@Setter
public class Book {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long bookId;
    @OneToMany(mappedBy = "bookId")
    private Set<PurchaseBook> purchaseBooks;

    @NotNull
    private String title;
    @NotNull
    private String author;
    @NotNull
    private LocalDate releaseDate;
    @NotNull
    private int numberOfPages;
    @NotNull
    private boolean status;
    @NotNull
    private int availablePieces;
    @NotNull
    private double price;
    private LocalDateTime createdOn;
    private LocalDateTime updatedOn;
}
