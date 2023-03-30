package com.bookstoreapplication.bookstore.book;

import lombok.Builder;
import lombok.Getter;

import java.time.LocalDate;

@Getter
@Builder
public class BookDto {

    private String title;
    private String author;
    private LocalDate releaseDate;
    private Integer numberOfPages;
    private Boolean status;
    private Integer availablePieces;
    private Double price;

}
