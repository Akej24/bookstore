package com.bookstoreapplication.bookstore.book;

import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

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

}
