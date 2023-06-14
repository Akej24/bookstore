package com.bookstoreapplication.bookstore.purchase.exception;

public class CheckoutCartNotFoundException extends OrderException{

    public CheckoutCartNotFoundException() {
        super("Checkout cart has not been found");
    }

}
