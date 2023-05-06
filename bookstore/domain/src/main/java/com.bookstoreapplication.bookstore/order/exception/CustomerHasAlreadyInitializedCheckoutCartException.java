package com.bookstoreapplication.bookstore.order.exception;

public class CustomerHasAlreadyInitializedCheckoutCartException extends OrderException {

    public CustomerHasAlreadyInitializedCheckoutCartException() {
        super("Customer has already initialized checkout cart, try to cancel it first");
    }

}
