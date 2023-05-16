package com.bookstoreapplication.bookstore.book;

import com.bookstoreapplication.bookstore.book.exception.BookDoesNotExistException;
import com.bookstoreapplication.bookstore.book.exception.BookWithTitleAndAuthorExistsException;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.cache.annotation.CachePut;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.validation.annotation.Validated;

import javax.validation.Valid;
import java.util.List;
import java.util.Set;

@Slf4j
@Validated
@AllArgsConstructor
class BookHandler {

    private final BookRepository bookRepository;

    @Transactional
    public void addBook(@Valid BookCommand source){
        if(bookRepository.existsByBookTitle_BookTitleAndBookAuthor_BookAuthor(source.getBookTitle().getBookTitle(), source.getBookAuthor().getBookAuthor())){
            log.warn("Book with title: {} and author: {} already exists", source.getBookTitle().getBookTitle(), source.getBookAuthor().getBookAuthor());
            throw new BookWithTitleAndAuthorExistsException();
        }
        bookRepository.save(new Book(source));
        log.info("Successfully added book to the database");
    }

    @Cacheable(cacheNames = "Book")
    public BookQueryResponse getBookById(long bookId){
        BookQueryResponse book = BookQueryResponse.toResponse(findBookById(bookId));
        log.info("Successively fetch a book with id {} from the database", bookId);
        return book;
    }

    @Cacheable(cacheNames = "Books")
    public Set<BookQueryResponse> getAllBooks(Specification<Book> specification, Pageable pageable) {
        List<Book> books = bookRepository.findAll(specification, pageable).getContent();
        log.info("All books have been fetched from the database");
        return BookQueryResponse.toResponses(books);
    }

    @Transactional
    public void deleteBook(long bookId) {
        existsBookById(bookId);
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
        Book bookToUpdate = bookRepository.findBookByBookId(bookId);
        bookRepository.save(bookToUpdate.update(source));
        log.info("The book with id {} has been updated", bookId);
    }

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

    int countAllBooks() {
        List<Book> books = bookRepository.findAll();
        log.info("All books have been fetched from the database");
        return books.size();
    }
}
