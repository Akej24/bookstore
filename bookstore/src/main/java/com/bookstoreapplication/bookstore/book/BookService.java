package com.bookstoreapplication.bookstore.book;

import lombok.AllArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.validation.annotation.Validated;

import javax.validation.Valid;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.time.LocalDate;
import java.util.List;

@Service
@Validated
@AllArgsConstructor
public class BookService {

    private static final int PAGE_SIZE = 20;
    private final BookRepository bookRepository;
    private final Logger logger = LoggerFactory.getLogger(BookService.class);

    @Transactional
    Book addBookToDatabase(@Valid BookRequest bookRequest){
        Book bookToSave = createFromRequest(bookRequest);
        Book savedBook = bookRepository.save(bookToSave);
        logger.info("Successfully added to the database");
        return savedBook;
    }

    Book getBookById(long bookId){
        Book book = bookRepository.findById(bookId).orElseThrow(() -> {
            logger.warn("The book with id {} does not exist", bookId);
            return new IllegalArgumentException("Book with given id does not exist");
        });
        logger.info("Successively fetch a book with id {} from the database", bookId);
        return book;
    }

    List<Book> getAllBooks(Integer page,
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
        logger.info("All books have been fetched from the database");
        return books;
    }

    @Transactional
    void deleteBook(long bookId) {
        Book bookToDelete = bookRepository.findById(bookId).orElseThrow(() -> {
            logger.warn("The book with id {} does not exist", bookId);
            throw new IllegalArgumentException("Book with given id does not exist");
        });
        bookRepository.delete(bookToDelete);
        logger.info("The book with id {} was successfully removed from the database", bookId);
    }

    @Transactional
    void deleteAllBooks() {
        bookRepository.deleteAll();
        logger.warn("All books have been removed from the database");
    }

    @Transactional
    Book updateBookById(long bookId, @Valid BookRequest bookRequest) {
        Book bookToUpdate = bookRepository.findById(bookId).orElseThrow(() -> {
            logger.warn("The book with id {} does not exist", bookId);
            throw new IllegalArgumentException("Book with given id does not exist");
        });
        Book updatedBook = updateFromRequest(bookToUpdate, bookRequest);
        Book savedBook = bookRepository.save(updatedBook);
        logger.info("The book with id {} has been updated", bookId);
        return savedBook;
    }

    public BigDecimal calculateBookPriceByAmount(long bookId, int amount){
        Book book = bookRepository.findById(bookId)
                .orElseThrow( () -> new IllegalArgumentException("Book with given id does not exist"));
        BigDecimal price = BigDecimal.valueOf(book.getPrice());
        BigDecimal total = price.multiply(BigDecimal.valueOf(amount));
        return total.setScale(2, RoundingMode.HALF_UP);
    }

    private static Book createFromRequest(BookRequest bookRequest) {
        return Book.builder()
                .title(bookRequest.getTitle())
                .author(bookRequest.getAuthor())
                .releaseDate(bookRequest.getReleaseDate())
                .numberOfPages(bookRequest.getNumberOfPages())
                .status(bookRequest.getStatus())
                .availablePieces(bookRequest.getAvailablePieces())
                .price(bookRequest.getPrice())
                .bookAudit(new BookAudit())
                .build();

    }

    private static Book updateFromRequest(Book bookToUpdate, BookRequest bookRequest) {
        Book book = bookToUpdate.toBuilder()
                .title(bookRequest.getTitle())
                .author(bookRequest.getAuthor())
                .releaseDate(bookRequest.getReleaseDate())
                .numberOfPages(bookRequest.getNumberOfPages())
                .status(bookRequest.getStatus())
                .availablePieces(bookRequest.getAvailablePieces())
                .price(bookRequest.getPrice())
                .build();
        book.setPiecesPrePersist();
        return book;
    }

}
