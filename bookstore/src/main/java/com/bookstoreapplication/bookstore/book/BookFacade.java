package com.bookstoreapplication.bookstore.book;

import com.bookstoreapplication.bookstore.book.query.SimpleBookQueryDto;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;

@Service
@AllArgsConstructor
public class BookFacade {

    private final BookService bookService;

    public void updateBookAvailablePieces(Integer booksAmount, Long bookId) {
        bookService.updateBookAvailablePieces(booksAmount, bookId);
    }

    public BigDecimal calculateBookPriceByAmount(Long bookId, Integer amount) {
        return bookService.calculateBookPriceByAmount(bookId, amount);
    }

    public SimpleBookQueryDto createNewSimpleBookQueryDto(Long bookId) {
        return bookService.createNewSimpleBookQueryDto(bookId);
    }

}
