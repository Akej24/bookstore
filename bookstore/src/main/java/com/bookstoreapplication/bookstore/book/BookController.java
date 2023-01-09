package com.bookstoreapplication.bookstore.book;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/bookform")
@AllArgsConstructor
class BookController {

    BookService bookService;

    @PostMapping
    void addBookToDatabase(@RequestBody BookWriteModel bookWriteModel){
        bookService.addBookToDatabase(bookWriteModel);
    }

}
