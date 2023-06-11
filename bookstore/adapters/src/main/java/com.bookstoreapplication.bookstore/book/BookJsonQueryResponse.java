package com.bookstoreapplication.bookstore.book;

import com.bookstoreapplication.bookstore.book.value_object.*;
import com.fasterxml.jackson.annotation.JsonUnwrapped;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.List;
import java.util.stream.Collectors;

@Getter
@AllArgsConstructor(access = AccessLevel.PRIVATE)
class BookJsonQueryResponse {

    private final long bookId;
    @JsonUnwrapped
    private final BookTitle bookTitle;
    @JsonUnwrapped
    private final BookAuthor bookAuthor;
    @JsonUnwrapped
    private final ReleaseDate releaseDate;
    @JsonUnwrapped
    private final NumberOfPages numberOfPages;
    @JsonUnwrapped
    private final AvailabilityStatus availabilityStatus;
    @JsonUnwrapped
    private final AvailablePieces availablePieces;
    @JsonUnwrapped
    private final BookPrice bookPrice;

    static List<BookJsonQueryResponse> from(List<BookQueryResponse> sources) {
        return sources.stream()
                .map(BookJsonQueryResponse::from)
                .collect(Collectors.toList());
    }

    static BookJsonQueryResponse from(BookQueryResponse source) {
        return new BookJsonQueryResponse(
                source.getBookId(),
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
