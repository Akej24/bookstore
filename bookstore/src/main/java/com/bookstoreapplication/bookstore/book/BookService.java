package com.bookstoreapplication.bookstore.book;

import lombok.AllArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.cache.annotation.CachePut;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.validation.Valid;
import java.time.LocalDate;
import java.util.List;

@Service
@AllArgsConstructor
class BookService {

    private static final int PAGE_SIZE = 20;
    private static final Logger LOGGER = LoggerFactory.getLogger(BookService.class);
    private final BookRepository bookRepository;

    @Transactional
    void addBookToDatabase(BookRequest source){
        bookRepository.save(BookFactory.createBook(source));
        LOGGER.info("Successfully added to the database");
    }

    @Cacheable(cacheNames = "Book")
    public BookDto getBookById(long bookId){
        BookDto book = findBookDtoById(bookId);
        LOGGER.info("Successively fetch a book with id {} from the database [Cached]", bookId);
        return book;
    }

    @Cacheable(cacheNames = "Books")
    public List<BookDto> getAllBooks(Integer page,
                                     String sortDirection,
                                     String sortBy,
                                     String title,
                                     String author,
                                     LocalDate releaseDate,
                                     Integer numberOfPages,
                                     Boolean status,
                                     String availablePieces,
                                     String price) {

        page = page != null && page >= 0 ? page : 0;
        Sort.Direction direction = sortDirection.equalsIgnoreCase("asc") ? Sort.Direction.ASC : Sort.Direction.DESC;
        Sort sort = Sort.by(direction, sortBy);
        Pageable pageable = PageRequest.of(page, PAGE_SIZE, sort);

        Specification<Book> specification = Specification.where(
                BookSpecifications.hasTitleContainingIgnoreCase(title)
                        .and(BookSpecifications.hasAuthorContainingIgnoreCase(author))
                        .and(BookSpecifications.hasReleaseDateAfter(releaseDate))
                        .and(BookSpecifications.hasNumberOfPagesGreaterThanOrEqual(numberOfPages))
                        .and(BookSpecifications.hasStatus(status))
                        .and(BookSpecifications.hasAvailablePiecesContainingIgnoreCase(availablePieces))
                        .and(BookSpecifications.hasPriceContainingIgnoreCase(price))
        );

        List<Book> books = bookRepository.findAll(specification, pageable).getContent();
        LOGGER.info("All books have been fetched from the database [Cached]");
        return BookDtoMapper.mapToBooksDto(books);
    }

    @Transactional
    void deleteBook(long bookId) {
        existsBookById(bookId);
        bookRepository.deleteBookByBookId_BookId(bookId);
        LOGGER.info("The book with id {} was successfully removed from the database", bookId);
    }

    @Transactional
    void deleteAllBooks() {
        bookRepository.deleteAllBy();
        LOGGER.warn("All books have been removed from the database");
    }

    @Transactional
    @CachePut(cacheNames = "Book", key = "#root.target.savedBook.bookId")
    public BookDto updateBookById(long bookId, @Valid BookRequest source) {
        Book bookToUpdate = bookRepository.findBookByBookId_BookId(bookId);
        Book updatedBook = BookFactory.updateBook(bookToUpdate, source);
        Book savedBook = bookRepository.save(updatedBook);
        LOGGER.info("The book with id {} has been updated [Cached]", bookId);
        return BookDtoMapper.mapToBookDto(savedBook);
    }

    BookDto findBookDtoById(long bookId) {
        existsBookById(bookId);
        return bookRepository.findBookDtoByBookId_BookId(bookId);
    }

    void existsBookById(long bookId){
        if(!bookRepository.existsById(bookId)){
            LOGGER.warn("Book with id {} does not exist", bookId);
            throw new IllegalArgumentException("Book with given id does not exist");
        }
    }

}
