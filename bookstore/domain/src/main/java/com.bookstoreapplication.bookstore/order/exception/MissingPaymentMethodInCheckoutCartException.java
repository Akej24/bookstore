package com.bookstoreapplication.bookstore.order.exception;

public class MissingPaymentMethodInCheckoutCartException extends OrderException {

    public MissingPaymentMethodInCheckoutCartException() {
        super("Add payment method in your checkout cart");
    }
}
