package com.bookstoreapplication.bookstore.book;

import com.bookstoreapplication.bookstore.book.exception.BookWithTitleAndAuthorExistsException;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.validation.annotation.Validated;

import javax.validation.Valid;
import java.util.List;

@Slf4j
@Validated
@AllArgsConstructor
class BookControllerHandler {

    private final BookSharedHandler bookSharedHandler;
    private final BookRepository bookRepository;

    @Transactional
    public void addBook(@Valid BookCommand source){
        if(bookRepository.existsByBookTitle_BookTitleAndBookAuthor_BookAuthor(
                source.bookTitle().getBookTitle(),
                source.bookAuthor().getBookAuthor())) {
            log.warn("Book with title: {} and author: {} already exists",
                    source.bookTitle().getBookTitle(),
                    source.bookAuthor().getBookAuthor());
            throw new BookWithTitleAndAuthorExistsException();
        }
        bookRepository.save(new Book(source));
        log.info("Successfully added book to the database");
    }

    @Cacheable(cacheNames = "Book")
    public BookQueryResponse getBookById(long bookId){
        BookQueryResponse book = BookQueryResponse.from(bookSharedHandler.findBookById(bookId));
        log.info("Successively fetch a book with id {} from the database", bookId);
        return book;
    }

    @Cacheable(cacheNames = "Books")
    public List<BookQueryResponse> getAllBooks(Specification<Book> specification, Pageable pageable) {
        List<Book> books = bookRepository.findAll(specification, pageable).getContent();
        log.info("All books have been fetched from the database");
        return BookQueryResponse.from(books);
    }

    @Transactional
    public void deleteBook(long bookId) {
        bookSharedHandler.existsBookById(bookId);
        bookRepository.deleteBookByBookId(bookId);
        log.info("The book with id {} was successfully removed from the database", bookId);
    }

    @Transactional
    public void deleteAllBooks() {
        bookRepository.deleteAllBy();
        log.warn("All books have been removed from the database");
    }

    @Transactional
    public void updateBookById(long bookId, @Valid BookCommand source) {
        Book bookToUpdate = bookSharedHandler.findBookById(bookId);
        bookRepository.save(bookToUpdate.update(source));
        log.info("The book with id {} has been updated", bookId);
    }

    int countAllBooks() {
        List<Book> books = bookRepository.findAll();
        log.info("All books have been fetched from the database");
        return books.size();
    }
}
