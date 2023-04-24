package com.bookstoreapplication.bookstore.book;

import com.bookstoreapplication.bookstore.purchase.value_object.SimpleBookId;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.cache.annotation.CachePut;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.validation.Valid;
import java.util.List;
import java.util.Set;

@Service
@AllArgsConstructor
@Slf4j
class BookCommandHandler {

    private final BookRepository bookRepository;

    @Transactional
    public void addBookToDatabase(BookCommand source){
        Book book = new Book(source);
        bookRepository.save(book);
        log.info("Successfully added to the database");
    }

    @Cacheable(cacheNames = "Book")
    public BookQueryResponse getBookById(SimpleBookId bookId){
        BookQueryResponse book = findBookDtoById(bookId.getBookId());
        log.info("Successively fetch a book with id {} from the database", bookId);
        return book;
    }

    @Cacheable(cacheNames = "Books")
    public Set<BookQueryResponse> getAllBooks(Specification<Book> specification, Pageable pageable) {
        List<Book> books = bookRepository.findAll(specification, pageable).getContent();
        log.info("All books have been fetched from the database [Cached]");
        return BookQueryResponse.toResponses(books);
    }

    @Transactional
    public void deleteBook(SimpleBookId bookId) {
        existsBookById(bookId.getBookId());
        bookRepository.deleteBookByBookId_BookId(bookId.getBookId());
        log.info("The book with id {} was successfully removed from the database", bookId);
    }

    @Transactional
    public void deleteAllBooks() {
        bookRepository.deleteAllBy();
        log.warn("All books have been removed from the database");
    }

    @Transactional
    @CachePut(cacheNames = "Book", key = "#root.target.savedBook.bookId")
    public void updateBookById(SimpleBookId bookId, @Valid BookCommand source) {
        Book bookToUpdate = bookRepository.findBookByBookId_BookId(bookId.getBookId());
        bookRepository.save(bookToUpdate.update(source));
        log.info("The book with id {} has been updated", bookId);
    }

    BookQueryResponse findBookDtoById(long bookId) {
        existsBookById(bookId);
        return bookRepository.findBookDtoByBookId_BookId(bookId);
    }

    void existsBookById(long bookId){
        if(!bookRepository.existsById(bookId)){
            log.warn("Book with id {} does not exist", bookId);
            throw new IllegalArgumentException("Book with given id does not exist");
        }
    }

}
