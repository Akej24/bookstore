package com.bookstoreapplication.bookstore.book;

import com.bookstoreapplication.bookstore.book.query.SimpleBookQueryDto;

import java.util.List;
import java.util.stream.Collectors;

public class BookDtoMapper {

    private BookDtoMapper() {
    }

    public static List<BookDto> mapToBooksDto(List<Book> books){
        return books.stream()
                .map(BookDtoMapper::mapToBookDto)
                .collect(Collectors.toList());
    }

    public static BookDto mapToBookDto(Book book){
        return BookDto.builder()
                .title(book.getTitle())
                .author(book.getAuthor())
                .releaseDate(book.getReleaseDate())
                .numberOfPages(book.getNumberOfPages())
                .status(book.getStatus())
                .availablePieces(book.getAvailablePieces())
                .price(book.getPrice())
                .build();
    }

    public static BookDto mapToBookDto(SimpleBookQueryDto book){
        return BookDto.builder()
                .title(book.getTitle())
                .author(book.getAuthor())
                .releaseDate(book.getReleaseDate())
                .numberOfPages(book.getNumberOfPages())
                .status(book.getStatus())
                .availablePieces(book.getAvailablePieces())
                .price(book.getPrice())
                .build();
    }

    public static SimpleBookQueryDto mapToSimpleBookDto(Book book) {
        return SimpleBookQueryDto.builder()
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
