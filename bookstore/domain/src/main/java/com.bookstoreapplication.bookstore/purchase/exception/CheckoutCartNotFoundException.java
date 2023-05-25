package com.bookstoreapplication.bookstore.purchase.exception;

public class CheckoutCartNotFoundException extends OrderException{

    public CheckoutCartNotFoundException() {
        super("Checkout cart for customer with given id does not exist");
    }

}
