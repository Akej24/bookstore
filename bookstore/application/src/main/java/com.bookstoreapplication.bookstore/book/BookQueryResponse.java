package com.bookstoreapplication.bookstore.book;

import com.bookstoreapplication.bookstore.book.value_object.*;
import com.fasterxml.jackson.annotation.JsonUnwrapped;
import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Getter
@AllArgsConstructor
class BookQueryResponse {

    @JsonUnwrapped
    private Title title;
    @JsonUnwrapped
    private Author author;
    @JsonUnwrapped
    private ReleaseDate releaseDate;
    @JsonUnwrapped
    private NumberOfPages numberOfPages;
    @JsonUnwrapped
    private AvailabilityStatus availabilityStatus;
    @JsonUnwrapped
    private AvailablePieces availablePieces;
    @JsonUnwrapped
    private Price price;

    static Set<BookQueryResponse> toResponses(List<Book> sources){
        return sources.stream().map(BookQueryResponse::toResponse).collect(Collectors.toSet());
    }

    static BookQueryResponse toResponse(Book source){
        return new BookQueryResponse(
                source.getTitle(),
                source.getAuthor(),
                source.getReleaseDate(),
                source.getNumberOfPages(),
                source.getAvailabilityStatus(),
                source.getAvailablePieces(),
                source.getPrice());
    }

}
