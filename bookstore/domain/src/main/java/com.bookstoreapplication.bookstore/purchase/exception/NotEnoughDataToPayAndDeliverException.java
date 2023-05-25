package com.bookstoreapplication.bookstore.purchase.exception;

public class NotEnoughDataToPayAndDeliverException extends OrderException {

    public NotEnoughDataToPayAndDeliverException() {
        super("There is not enough data to pay for and deliver your order");
    }
}
