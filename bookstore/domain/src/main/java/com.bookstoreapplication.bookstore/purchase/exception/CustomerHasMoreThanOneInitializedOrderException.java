package com.bookstoreapplication.bookstore.purchase.exception;

public class CustomerHasMoreThanOneInitializedOrderException extends OrderException {

    public CustomerHasMoreThanOneInitializedOrderException() {
        super("Customer has more than one initialized order");
    }
}
