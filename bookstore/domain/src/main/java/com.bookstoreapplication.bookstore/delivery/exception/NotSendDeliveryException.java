package com.bookstoreapplication.bookstore.delivery.exception;

public class NotSendDeliveryException extends DeliveryException{

    public NotSendDeliveryException() {
        super("Delivery cannot be marked as received, because it has not been send");
    }
}
