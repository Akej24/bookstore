package com.bookstoreapplication.bookstore.order.exception;

public class CustomerNotFoundException extends OrderException {

    public CustomerNotFoundException() {
        super("Customer with given id has not been found");
    }

}
