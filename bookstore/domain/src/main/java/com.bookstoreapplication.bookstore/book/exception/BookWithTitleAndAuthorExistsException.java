package com.bookstoreapplication.bookstore.book.exception;

public class BookWithTitleAndAuthorExistsException extends BookException{

    public BookWithTitleAndAuthorExistsException() {
        super("Book with given title and author already exists in database");
    }

}
