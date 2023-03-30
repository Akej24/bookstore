package com.bookstoreapplication.bookstore.book;

import com.bookstoreapplication.bookstore.book.query.SimpleBookQueryDto;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.math.RoundingMode;

@Service
@AllArgsConstructor
public class BookFacade {

    private final BookRepository bookRepository;
    private final BookService bookService;

    public SimpleBookQueryDto createNewSimpleBookQueryDto(Long bookId) {
        Book book = bookService.findBookById(bookId);
        return BookDtoMapper.mapToSimpleBookDto(book);
    }

    public BigDecimal calculateBookPriceByAmount(Long bookId, Integer amount){
        Book book = bookService.findBookById(bookId);
        return BigDecimal
                .valueOf(book.getPrice())
                .multiply(BigDecimal.valueOf(amount))
                .setScale(2, RoundingMode.HALF_UP);
    }

    public void updateBookAvailablePieces(Integer booksAmount, Long bookId) {
        Book book = bookService.findBookById(bookId);
        if(book.getAvailablePieces() < booksAmount){
            throw new IllegalArgumentException("Not enough available pieces to buy this product");
        }else{
            Book updatedBook = book.toBuilder().availablePieces(book.getAvailablePieces() - booksAmount).build();
            bookRepository.save(updatedBook);
        }
    }

}
