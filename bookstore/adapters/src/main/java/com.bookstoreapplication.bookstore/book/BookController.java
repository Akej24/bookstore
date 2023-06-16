package com.bookstoreapplication.bookstore.book;

import dev.mccue.json.Json;
import lombok.AllArgsConstructor;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.List;

@RestController
@RequestMapping("/api/v1/books")
@AllArgsConstructor
class BookController {

    private static final int PAGE_SIZE = 15;
    private final BookControllerHandler bookControllerHandler;

    @PostMapping("")
    ResponseEntity<?> addBookToDatabase(@RequestBody Json json){
        bookControllerHandler.addBook(BookJsonCommand.fromJson(json));
        return ResponseEntity.status(HttpStatus.CREATED).build();
    }

    @GetMapping("/{bookId}")
    ResponseEntity<BookJsonQueryResponse> getBookById(@PathVariable long bookId){
        return new ResponseEntity<>(BookJsonQueryResponse.from(bookControllerHandler.getBookById(bookId)), HttpStatus.OK);
    }

    @GetMapping("")
    ResponseEntity<List<BookJsonQueryResponse>> getAllBooks(
            @RequestParam(required = false) Integer page,
            @RequestParam(required = false, defaultValue = "releaseDate") String sortBy,
            @RequestParam(required = false, defaultValue = "asc") String sortDirection,
            @RequestParam(required = false) String bookTitle,
            @RequestParam(required = false) String bookAuthor,
            @RequestParam(required = false) LocalDate releaseDate,
            @RequestParam(required = false) Integer numberOfPages,
            @RequestParam(required = false) Boolean availabilityStatus,
            @RequestParam(required = false) String availablePieces,
            @RequestParam(required = false) String bookPrice
            ){

        page = page != null && page >= 0 ? page : 0;
        Sort.Direction direction = sortDirection.equalsIgnoreCase("asc") ? Sort.Direction.ASC : Sort.Direction.DESC;
        Sort sort = Sort.by(direction, sortBy);
        Pageable pageable = PageRequest.of(page, PAGE_SIZE, sort);

        Specification<Book> specification = Specification.where(
                BookSpecifications.hasTitleContainingIgnoreCase(bookTitle)
                        .and(BookSpecifications.hasAuthorContainingIgnoreCase(bookAuthor))
                        .and(BookSpecifications.hasReleaseDateAfter(releaseDate))
                        .and(BookSpecifications.hasNumberOfPagesGreaterThanOrEqual(numberOfPages))
                        .and(BookSpecifications.hasStatus(availabilityStatus))
                        .and(BookSpecifications.hasAvailablePiecesContainingIgnoreCase(availablePieces))
                        .and(BookSpecifications.hasPriceContainingIgnoreCase(bookPrice))
        );
        List<BookQueryResponse> booksJsonResponse = bookControllerHandler.getAllBooks(specification, pageable);
        return new ResponseEntity<>(BookJsonQueryResponse.from(booksJsonResponse), HttpStatus.OK);
    }

    @DeleteMapping("/{bookId}")
    ResponseEntity<?> deleteBookById(@PathVariable long bookId){
        bookControllerHandler.deleteBook(bookId);
        return ResponseEntity.status(HttpStatus.OK).build();
    }

    @DeleteMapping("")
    ResponseEntity<?> deleteAllBooks(){
        bookControllerHandler.deleteAllBooks();
        return ResponseEntity.status(HttpStatus.OK).build();
    }

    @PutMapping("/{bookId}")
    ResponseEntity<?> updateBookById(@PathVariable long bookId, @RequestBody Json json){
        bookControllerHandler.updateBookById(bookId, BookJsonCommand.fromJson(json));
        return ResponseEntity.status(HttpStatus.OK).build();
    }
}
