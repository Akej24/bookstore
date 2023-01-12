package com.bookstoreapplication.bookstore.book;

import com.bookstoreapplication.bookstore.purchase.PurchaseBook;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.util.Set;

@Entity
@Table(name = "books")
@Getter
@AllArgsConstructor
@NoArgsConstructor
@Builder
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
    private BookAudit bookAudit = new BookAudit();

}
