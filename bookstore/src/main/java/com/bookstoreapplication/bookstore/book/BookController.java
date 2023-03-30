package com.bookstoreapplication.bookstore.book;

import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.List;

@RestController
@RequestMapping("/books")
@AllArgsConstructor
@CrossOrigin("http://localhost:3000")
class BookController {

    private final BookService bookService;

    @PostMapping("")
    ResponseEntity<?> addBookToDatabase(@RequestBody BookRequest bookRequest){
        bookService.addBookToDatabase(bookRequest);
        return ResponseEntity.status(HttpStatus.CREATED).build();
    }

    @GetMapping("/{bookId}")
    ResponseEntity<BookDto> getBookById(@PathVariable long bookId){
        return new ResponseEntity<>(bookService.getBookById(bookId), HttpStatus.OK);
    }

    @GetMapping("")
    ResponseEntity<List<BookDto>> getAllBooks(
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
        return new ResponseEntity<>(bookService.getAllBooks(page, sortDirection, sortBy, title, author, releaseDate, numberOfPages, status, availablePieces, price), HttpStatus.OK);
    }

    @DeleteMapping("/{bookId}")
    ResponseEntity<?> deleteBookById(@PathVariable long bookId){
        bookService.deleteBook(bookId);
        return ResponseEntity.status(HttpStatus.OK).build();
    }

    @DeleteMapping("")
    ResponseEntity<?> deleteAllBooks(){
        bookService.deleteAllBooks();
        return ResponseEntity.status(HttpStatus.OK).build();
    }

    @PutMapping("/{bookId}")
    ResponseEntity<BookDto> updateBookById(@PathVariable long bookId, @RequestBody BookRequest bookRequest){
        return new ResponseEntity<>(bookService.updateBookById(bookId, bookRequest), HttpStatus.OK);
    }

}
