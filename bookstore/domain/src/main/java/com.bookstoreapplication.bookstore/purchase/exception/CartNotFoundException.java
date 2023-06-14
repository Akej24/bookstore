package com.bookstoreapplication.bookstore.purchase.exception;

public class CartNotFoundException extends OrderException{

    public CartNotFoundException() {
        super("Cart has not been found");
    }
}
