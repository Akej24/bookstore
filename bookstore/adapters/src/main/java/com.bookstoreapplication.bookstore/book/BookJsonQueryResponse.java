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

    static List<BookJsonQueryResponse> from(List<BookQueryResponse> sources) {
        return sources.stream()
                .map(BookJsonQueryResponse::from)
                .collect(Collectors.toList());
    }

    static BookJsonQueryResponse from(BookQueryResponse source) {
        return new BookJsonQueryResponse(
                source.getBookTitle(),
                source.getBookAuthor(),
                source.getReleaseDate(),
                source.getNumberOfPages(),
                source.getAvailabilityStatus(),
                source.getAvailablePieces(),
                source.getBookPrice()
        );
    }
}
