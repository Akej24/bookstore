package com.bookstoreapplication.bookstore.book;

import com.bookstoreapplication.bookstore.book.value_object.*;
import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.List;
import java.util.stream.Collectors;

@Getter
@AllArgsConstructor
class BookQueryResponse {

    private BookTitle bookTitle;
    private BookAuthor bookAuthor;
    private ReleaseDate releaseDate;
    private NumberOfPages numberOfPages;
    private AvailabilityStatus availabilityStatus;
    private AvailablePieces availablePieces;
    private BookPrice bookPrice;

    static List<BookQueryResponse> toResponses(List<Book> sources){
        return sources.stream().map(BookQueryResponse::toResponse).collect(Collectors.toList());
    }

    static BookQueryResponse toResponse(Book source){
        return new BookQueryResponse(
                source.getBookTitle(),
                source.getBookAuthor(),
                source.getReleaseDate(),
                source.getNumberOfPages(),
                source.getAvailabilityStatus(),
                source.getAvailablePieces(),
                source.getBookPrice());
    }

}
