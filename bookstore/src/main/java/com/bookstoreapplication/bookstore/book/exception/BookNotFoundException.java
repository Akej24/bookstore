package com.bookstoreapplication.bookstore.book.exception;

public class BookNotFoundException extends RuntimeException{

    public BookNotFoundException() {
        super("Book with given id does not exist");
    }

}
