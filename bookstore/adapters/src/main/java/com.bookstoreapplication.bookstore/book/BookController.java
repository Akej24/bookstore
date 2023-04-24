package com.bookstoreapplication.bookstore.book;

import com.bookstoreapplication.bookstore.purchase.value_object.SimpleBookId;
import lombok.AllArgsConstructor;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.time.LocalDate;
import java.util.Set;

@RestController
@RequestMapping("/books")
@AllArgsConstructor
@Validated
@CrossOrigin("http://localhost:3000")
class BookController {

    private static final int PAGE_SIZE = 20;
    private final BookCommandHandler bookCommandHandler;

    @PostMapping("")
    ResponseEntity<?> addBookToDatabase(@RequestBody @Valid BookCommand bookCommand){
        bookCommandHandler.addBookToDatabase(bookCommand);
        return ResponseEntity.status(HttpStatus.CREATED).build();
    }

    @GetMapping("/{bookId}")
    ResponseEntity<BookQueryResponse> getBookById(@PathVariable @Valid SimpleBookId bookId){
        return new ResponseEntity<>(bookCommandHandler.getBookById(bookId), HttpStatus.OK);
    }

    @GetMapping("")
    ResponseEntity<Set<BookQueryResponse>> getAllBooks(
            @RequestParam(required = false) Integer page,
            @RequestParam(required = false, defaultValue = "releaseDate") String sortBy,
            @RequestParam(required = false, defaultValue = "asc") String sortDirection,
            @RequestParam(required = false) String title,
            @RequestParam(required = false) String author,
            @RequestParam(required = false) LocalDate releaseDate,
            @RequestParam(required = false) Integer numberOfPages,
            @RequestParam(required = false) Boolean status,
            @RequestParam(required = false) String availablePieces,
            @RequestParam(required = false) String price
            ){

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

        return new ResponseEntity<>(bookCommandHandler.getAllBooks(specification, pageable), HttpStatus.OK);
    }

    @DeleteMapping("/{bookId}")
    ResponseEntity<?> deleteBookById(@PathVariable @Valid SimpleBookId bookId){
        bookCommandHandler.deleteBook(bookId);
        return ResponseEntity.status(HttpStatus.OK).build();
    }

    @DeleteMapping("")
    ResponseEntity<?> deleteAllBooks(){
        bookCommandHandler.deleteAllBooks();
        return ResponseEntity.status(HttpStatus.OK).build();
    }

    @PutMapping("/{bookId}")
    ResponseEntity<?> updateBookById(@PathVariable SimpleBookId bookId, @RequestBody BookCommand bookCommand){
        bookCommandHandler.updateBookById(bookId, bookCommand);
        return ResponseEntity.status(HttpStatus.OK).build();
    }

}
