package com.bookstoreapplication.bookstore.order.exception;

public class CustomerHasAlreadyInitializedOrderException extends OrderException {

    public CustomerHasAlreadyInitializedOrderException() {
        super("Customer has already one initialized order");
    }
}
