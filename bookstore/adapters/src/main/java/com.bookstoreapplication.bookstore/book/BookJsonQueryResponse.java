package com.bookstoreapplication.bookstore.book;

import com.bookstoreapplication.bookstore.book.value_object.*;
import com.fasterxml.jackson.annotation.JsonUnwrapped;
import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.List;
import java.util.stream.Collectors;

@Getter
@AllArgsConstructor
class BookJsonQueryResponse {

    @JsonUnwrapped
    private BookTitle bookTitle;
    @JsonUnwrapped
    private BookAuthor bookAuthor;
    @JsonUnwrapped
    private ReleaseDate releaseDate;
    @JsonUnwrapped
    private NumberOfPages numberOfPages;
    @JsonUnwrapped
    private AvailabilityStatus availabilityStatus;
    @JsonUnwrapped
    private AvailablePieces availablePieces;
    @JsonUnwrapped
    private BookPrice bookPrice;

    public BookJsonQueryResponse(BookQueryResponse source) {
        this.bookTitle = source.getBookTitle();
        this.bookAuthor = source.getBookAuthor();
        this.releaseDate = source.getReleaseDate();
        this.numberOfPages = source.getNumberOfPages();
        this.availabilityStatus = source.getAvailabilityStatus();
        this.availablePieces = source.getAvailablePieces();
        this.bookPrice = source.getBookPrice();
    }

    public static List<BookJsonQueryResponse> toList(List<BookQueryResponse> sources) {
        return sources.stream().map(BookJsonQueryResponse::new).collect(Collectors.toList());
    }
}
