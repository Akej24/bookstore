package com.bookstoreapplication.bookstore.delivery.exception;

public class DeliveryNotFoundException extends DeliveryException {

    public DeliveryNotFoundException() {
        super("Delivery with given id has not been found");
    }
}
