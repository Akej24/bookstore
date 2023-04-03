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
        Book book = bookRepository.findBookByBookId_BookId(bookId);
        return BookDtoMapper.mapToSimpleBookDto(book);
    }

    public BigDecimal calculateBookPriceByAmount(Long bookId, Integer amount){
        Book book = bookRepository.findBookByBookId_BookId(bookId);
        return book.getPrice().price()
                .multiply(BigDecimal.valueOf(amount))
                .setScale(2, RoundingMode.HALF_UP);
    }

    public void updateBookAvailablePieces(Integer booksAmount, Long bookId) {
        Book book = bookRepository.findBookByBookId_BookId(bookId);
        if(book.getAvailablePieces().availablePieces() < booksAmount){
            throw new IllegalArgumentException("Not enough available pieces to buy this product");
        }else{
//            Book updatedBook = book.toBuilder().availablePieces(book.getAvailablePieces() - booksAmount).build();
//            bookRepository.save(updatedBook);
        }
    }

}
