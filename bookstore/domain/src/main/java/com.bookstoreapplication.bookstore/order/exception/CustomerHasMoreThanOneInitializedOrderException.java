package com.bookstoreapplication.bookstore.order.exception;

public class CustomerHasMoreThanOneInitializedOrderException extends OrderException {

    public CustomerHasMoreThanOneInitializedOrderException() {
        super("Customer has more than one initialized order");
    }
}
