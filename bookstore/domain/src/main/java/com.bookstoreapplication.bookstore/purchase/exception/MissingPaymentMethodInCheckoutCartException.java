package com.bookstoreapplication.bookstore.purchase.exception;

public class MissingPaymentMethodInCheckoutCartException extends OrderException {

    public MissingPaymentMethodInCheckoutCartException() {
        super("Add payment method in your checkout cart");
    }
}
