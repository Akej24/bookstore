package com.bookstoreapplication.bookstore.delivery.exception;

public class DeliveryAlreadySendException extends DeliveryException {

    public DeliveryAlreadySendException() {
        super("Delivery has been already sent");
    }
}
