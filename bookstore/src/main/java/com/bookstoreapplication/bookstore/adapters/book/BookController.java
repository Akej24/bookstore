package com.bookstoreapplication.bookstore.adapters.controller;

import com.bookstoreapplication.bookstore.application.book.BookCommand;
import com.bookstoreapplication.bookstore.application.book.BookCommandHandler;
import com.bookstoreapplication.bookstore.application.book.BookCommandResponse;
import com.bookstoreapplication.bookstore.domain.purchase.value_object.SimpleBookId;
import lombok.AllArgsConstructor;
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

    private final BookCommandHandler bookCommandHandler;

    @PostMapping("")
    ResponseEntity<?> addBookToDatabase(@RequestBody @Valid BookCommand bookCommand){
        bookCommandHandler.addBookToDatabase(bookCommand);
        return ResponseEntity.status(HttpStatus.CREATED).build();
    }

    @GetMapping("/{bookId}")
    ResponseEntity<BookCommandResponse> getBookById(@PathVariable @Valid SimpleBookId bookId){
        return new ResponseEntity<>(bookCommandHandler.getBookById(bookId), HttpStatus.OK);
    }

    @GetMapping("")
    ResponseEntity<Set<BookCommandResponse>> getAllBooks(
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
        return new ResponseEntity<>(bookCommandHandler.getAllBooks(page, sortDirection, sortBy, title, author, releaseDate, numberOfPages, status, availablePieces, price), HttpStatus.OK);
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
