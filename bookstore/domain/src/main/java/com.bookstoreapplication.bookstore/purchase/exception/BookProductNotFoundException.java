package com.bookstoreapplication.bookstore.purchase.exception;

public class BookProductNotFoundException extends OrderException {

    public BookProductNotFoundException() {
        super("Book product with given id has not been found");
    }
}
