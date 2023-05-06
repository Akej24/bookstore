package com.bookstoreapplication.bookstore.order.exception;

public class MissingPaymentInCheckoutCartException extends OrderException {

    public MissingPaymentInCheckoutCartException() {
        super("Add payment method in your checkout cart");
    }
}
