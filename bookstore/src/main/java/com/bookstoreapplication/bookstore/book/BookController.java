package com.bookstoreapplication.bookstore.book;

import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/books")
@AllArgsConstructor
@CrossOrigin("http://localhost:3000")
class BookController {

    BookService bookService;

    @PostMapping("/create")
    ResponseEntity<BookDatabaseModel> addBookToDatabase(@RequestBody BookWriteModel bookWriteModel){
        var createdBook = bookService.addBookToDatabase(bookWriteModel);
        return ResponseEntity.status(HttpStatus.CREATED).body(createdBook);
    }

    @GetMapping("/{bookId}")
    ResponseEntity<BookDatabaseModel> getBookById(@PathVariable long bookId){
        return new ResponseEntity<>(bookService.getBookById(bookId), HttpStatus.OK);
    }

    @GetMapping("")
    ResponseEntity<List<BookDatabaseModel>> getAllBooks(){
        return new ResponseEntity<>(bookService.getAllBooks(), HttpStatus.OK);
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
    ResponseEntity<BookDatabaseModel> updateBookById(@PathVariable long bookId, @RequestBody BookWriteModel bookWriteModel){
        return new ResponseEntity<>(bookService.updateBookById(bookId, bookWriteModel), HttpStatus.OK);
    }

}
