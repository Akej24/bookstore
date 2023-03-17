package com.bookstoreapplication.bookstore.book;

import java.util.List;
import java.util.stream.Collectors;

public class BookResponseMapper {

    private BookResponseMapper() {
    }

    public static List<BookResponse> mapToBooksResponse(List<Book> books){
        return books.stream()
                .map(BookResponseMapper::mapToBookResponse)
                .collect(Collectors.toList());
    }

    public static BookResponse mapToBookResponse(Book book){
        return BookResponse.builder()
                .title(book.getTitle())
                .author(book.getAuthor())
                .releaseDate(book.getReleaseDate())
                .numberOfPages(book.getNumberOfPages())
                .status(book.getStatus())
                .availablePieces(book.getAvailablePieces())
                .price(book.getPrice())
                .build();
    }

}
