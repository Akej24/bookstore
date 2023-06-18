package com.bookstoreapplication.bookstore.delivery.exception;

public class DeliveryAlreadyReceivedException extends DeliveryException {

    public DeliveryAlreadyReceivedException() {
        super("Delivery has been already received");
    }
}
