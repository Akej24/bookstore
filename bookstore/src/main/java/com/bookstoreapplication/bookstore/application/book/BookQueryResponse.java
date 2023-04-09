package com.bookstoreapplication.bookstore.application.book;

import com.bookstoreapplication.bookstore.domain.book.core.Book;
import com.bookstoreapplication.bookstore.domain.book.value_object.*;
import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;

import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Getter
@AllArgsConstructor
@EqualsAndHashCode
public class BookCommandResponse {

    private Title title;
    private Author author;
    private ReleaseDate releaseDate;
    private NumberOfPages numberOfPages;
    private AvailabilityStatus availabilityStatus;
    private AvailablePieces availablePieces;
    private Price price;

    static Set<BookCommandResponse> toResponses(List<Book> sources){
        return sources.stream().map(BookCommandResponse::toResponse).collect(Collectors.toSet());
    }

    static BookCommandResponse toResponse(Book source){
        return new BookCommandResponse(
                source.getTitle(),
                source.getAuthor(),
                source.getReleaseDate(),
                source.getNumberOfPages(),
                source.getAvailabilityStatus(),
                source.getAvailablePieces(),
                source.getPrice());
    }

}
