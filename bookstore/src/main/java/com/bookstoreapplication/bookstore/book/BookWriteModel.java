package com.bookstoreapplication.bookstore.book;

import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class BookWriteModel {

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

}
