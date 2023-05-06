package com.bookstoreapplication.bookstore.purchase.exception;

public class CustomerHasAlreadyInitializedOrderException extends OrderException {

    public CustomerHasAlreadyInitializedOrderException() {
        super("Customer has already one initialized order");
    }
}
