package com.bookstoreapplication.bookstore.order.exception;

public class MissingDeliveryDetailsInCheckoutCartException extends OrderException {

    public MissingDeliveryDetailsInCheckoutCartException() {
        super("Add delivery details in your checkout cart");
    }
}
