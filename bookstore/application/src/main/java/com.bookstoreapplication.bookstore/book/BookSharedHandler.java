package com.bookstoreapplication.bookstore.book;

import com.bookstoreapplication.bookstore.book.exception.BookDoesNotExistException;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@AllArgsConstructor
class BookSharedHandler {

    private final BookRepository bookRepository;

    Book findBookById(long bookId) {
        existsBookById(bookId);
        return bookRepository.findBookByBookId(bookId);
    }

    void existsBookById(long bookId){
        if(!bookRepository.existsByBookId(bookId)){
            log.warn("Book with id {} does not exist", bookId);
            throw new BookDoesNotExistException();
        }
    }

}
