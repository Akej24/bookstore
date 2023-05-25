package com.bookstoreapplication.bookstore.purchase.exception;

public class CartNotFoundException extends OrderException{

    public CartNotFoundException() {
        super("Cart for customer with given id has not been found");
    }
}
