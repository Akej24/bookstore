package com.bookstoreapplication.bookstore.purchase.exception;

public class CustomerDoesNotHaveAnyInitializedOrderException extends OrderException {

    public CustomerDoesNotHaveAnyInitializedOrderException() {
        super("Customer with given id does not have any initialized orders");
    }
}
