package com.bookstoreapplication.bookstore.purchase.exception;

public class BookProductAlreadyExistsInCartException extends OrderException {

    public BookProductAlreadyExistsInCartException() {
        super("Book product with given already exists in cart, try to increment this book or add another book");
    }
}
