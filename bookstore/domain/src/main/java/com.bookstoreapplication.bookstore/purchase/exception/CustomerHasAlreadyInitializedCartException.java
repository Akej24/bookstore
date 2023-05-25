package com.bookstoreapplication.bookstore.purchase.exception;

public class CustomerHasAlreadyInitializedCartException extends OrderException{

    public CustomerHasAlreadyInitializedCartException() {
        super("Customer with given id has already initialized cart, try to add product instead of create new cart");
    }
}
