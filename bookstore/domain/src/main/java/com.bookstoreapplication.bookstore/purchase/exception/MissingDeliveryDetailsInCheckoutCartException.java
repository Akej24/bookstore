package com.bookstoreapplication.bookstore.purchase.exception;

public class MissingDeliveryDetailsInCheckoutCartException extends OrderException {

    public MissingDeliveryDetailsInCheckoutCartException() {
        super("Add delivery details in your checkout cart");
    }
}
