package com.bookstoreapplication.bookstore.book;

import com.bookstoreapplication.bookstore.purchase.value_object.BooksAmount;
import lombok.AllArgsConstructor;
import lombok.Getter;

import javax.validation.Valid;

@Getter
@AllArgsConstructor
class BooksDecrementCommand {

    private long bookId;
    @Valid
    private BooksAmount booksAmount;

}
