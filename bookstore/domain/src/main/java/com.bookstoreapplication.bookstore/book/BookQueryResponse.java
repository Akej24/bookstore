package com.bookstoreapplication.bookstore.book;

import com.bookstoreapplication.bookstore.book.value_object.*;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.List;
import java.util.stream.Collectors;

@Getter
@AllArgsConstructor(access = AccessLevel.PRIVATE)
class BookQueryResponse {

    private final long bookId;
    private final BookTitle bookTitle;
    private final BookAuthor bookAuthor;
    private final ReleaseDate releaseDate;
    private final NumberOfPages numberOfPages;
    private final AvailabilityStatus availabilityStatus;
    private final AvailablePieces availablePieces;
    private final BookPrice bookPrice;

    static List<BookQueryResponse> from(List<Book> sources){
        return sources.stream()
                .map(BookQueryResponse::from)
                .collect(Collectors.toList());
    }

    static BookQueryResponse from(Book source){
        return new BookQueryResponse(
                source.getBookId(),
                source.getBookTitle(),
                source.getBookAuthor(),
                source.getReleaseDate(),
                source.getNumberOfPages(),
                source.getAvailabilityStatus(),
                source.getAvailablePieces(),
                source.getBookPrice());
    }

}
