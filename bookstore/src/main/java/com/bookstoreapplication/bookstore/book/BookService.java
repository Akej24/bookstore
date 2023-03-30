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
import org.springframework.validation.annotation.Validated;

import javax.validation.Valid;
import java.time.LocalDate;
import java.util.List;

@Service
@Validated
@AllArgsConstructor
class BookService {

    private static final int PAGE_SIZE = 20;
    private static final Logger LOGGER = LoggerFactory.getLogger(BookService.class);
    private final BookRepository bookRepository;

    @Transactional
    void addBookToDatabase(@Valid BookRequest bookRequest){
        bookRepository.save(createFromRequest(bookRequest));
        LOGGER.info("Successfully added to the database");
    }

    @Cacheable(cacheNames = "Book")
    public BookDto getBookById(long bookId){
        Book book = findBookById(bookId);
        LOGGER.info("Successively fetch a book with id {} from the database [Cached]", bookId);
        return BookDtoMapper.mapToBookDto(book);
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
        Book bookToDelete = bookRepository.findById(bookId).orElseThrow(() -> {
            LOGGER.warn("The book with id {} does not exist", bookId);
            throw new IllegalArgumentException("Book with given id does not exist");
        });
        bookRepository.delete(bookToDelete);
        LOGGER.info("The book with id {} was successfully removed from the database", bookId);
    }

    @Transactional
    void deleteAllBooks() {
        bookRepository.deleteAll();
        LOGGER.warn("All books have been removed from the database");
    }

    @Transactional
    @CachePut(cacheNames = "Book", key = "#root.target.savedBook.bookId")
    public BookDto updateBookById(long bookId, @Valid BookRequest bookRequest) {
        Book bookToUpdate = findBookById(bookId);
        Book updatedBook = updateFromRequest(bookToUpdate, bookRequest);
        Book savedBook = bookRepository.save(updatedBook);
        LOGGER.info("The book with id {} has been updated [Cached]", bookId);
        return BookDtoMapper.mapToBookDto(savedBook);
    }

    Book findBookById(Long bookId) {
        return bookRepository.findById(bookId).orElseThrow( () -> {
            LOGGER.warn("Book with id {} does not exist", bookId);
            throw new IllegalArgumentException("Book with given id does not exist");
        });
    }

    private Book createFromRequest(BookRequest bookRequest) {
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

    private Book updateFromRequest(Book bookToUpdate, BookRequest bookRequest) {
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
