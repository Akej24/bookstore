package com.bookstoreapplication.bookstore.purchase.exception;

public class NotEnoughBooksInMagazineException extends OrderException {

    public NotEnoughBooksInMagazineException() {
        super("Not enough pieces in magazine");
    }
}
