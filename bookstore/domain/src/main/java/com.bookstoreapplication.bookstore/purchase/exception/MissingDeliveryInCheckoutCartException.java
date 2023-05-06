package com.bookstoreapplication.bookstore.purchase.exception;

public class MissingDeliveryInCheckoutCartException extends OrderException {

    public MissingDeliveryInCheckoutCartException() {
        super("Add delivery details in your checkout cart");
    }
}
