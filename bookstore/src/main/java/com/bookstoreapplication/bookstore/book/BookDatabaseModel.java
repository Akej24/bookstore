package com.bookstoreapplication.bookstore.book;

import com.bookstoreapplication.bookstore.purchase.PurchaseBook;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;
import java.util.Set;

@Entity
@Table(name = "books")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class BookDatabaseModel {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long bookId;
    @OneToMany(mappedBy = "bookId")
    private Set<PurchaseBook> purchaseBooks;
    ///
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
    @Embedded
    private BookAudit userAudit = new BookAudit();

    public BookDatabaseModel(String title, String author, LocalDate releaseDate, int numberOfPages, boolean status, int availablePieces, double price) {
        this.title = title;
        this.author = author;
        this.releaseDate = releaseDate;
        this.numberOfPages = numberOfPages;
        this.status = status;
        this.availablePieces = availablePieces;
        this.price = price;
    }
}