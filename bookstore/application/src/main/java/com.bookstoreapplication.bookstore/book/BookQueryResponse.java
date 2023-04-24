package com.bookstoreapplication.bookstore.book;

import com.bookstoreapplication.bookstore.book.value_object.*;
import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Getter
@AllArgsConstructor
class BookQueryResponse {

    private Title title;
    private Author author;
    private ReleaseDate releaseDate;
    private NumberOfPages numberOfPages;
    private AvailabilityStatus availabilityStatus;
    private AvailablePieces availablePieces;
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
