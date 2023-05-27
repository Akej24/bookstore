package com.bookstoreapplication.bookstore.book;

import com.bookstoreapplication.bookstore.purchase.value_object.BooksAmount;

import javax.validation.Valid;

record BooksDecrementCommand(

        long bookId,
        @Valid BooksAmount booksAmount

) { }
