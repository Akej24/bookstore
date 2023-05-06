package com.bookstoreapplication.bookstore.order.exception;

public class NotEnoughBooksInMagazineException extends OrderException {

    public NotEnoughBooksInMagazineException() {
        super("There is not enough books amount in magazine");
    }
}
