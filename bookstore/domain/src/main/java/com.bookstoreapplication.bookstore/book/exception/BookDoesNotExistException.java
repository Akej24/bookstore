package com.bookstoreapplication.bookstore.book.exception;

public class BookDoesNotExistException extends BookException{

    public BookDoesNotExistException() {
        super("Book with given id does not exist");
    }
}
