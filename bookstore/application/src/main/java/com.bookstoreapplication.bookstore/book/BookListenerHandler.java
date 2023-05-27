package com.bookstoreapplication.bookstore.book;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.validation.annotation.Validated;

import javax.validation.Valid;
import java.util.List;
import java.util.stream.Collectors;

@Slf4j
@Validated
@AllArgsConstructor
class BookListenerHandler {

    private final BookSharedHandler bookSharedHandler;
    private final BookRepository bookRepository;

    @Transactional
    public void decrementBooksAmount(List<@Valid BooksDecrementCommand> booksDecrementCommand){
        List<Book> decrementedBooks = booksDecrementCommand.stream()
                .map( bookToDecrement -> {
                    Book book = bookSharedHandler.findBookById(bookToDecrement.bookId());
                    return book.decreaseAvailablePieces(bookToDecrement.booksAmount());
                })
                .collect(Collectors.toList());
        bookRepository.saveAll(decrementedBooks);
        log.info("Books successfully decremented");
    }

}
