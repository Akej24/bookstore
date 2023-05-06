package com.bookstoreapplication.bookstore.purchase;

import com.bookstoreapplication.bookstore.purchase.exception.MissingDeliveryInCheckoutCartException;
import com.bookstoreapplication.bookstore.purchase.exception.MissingPaymentInCheckoutCartException;
import lombok.Getter;

@Getter
class CheckoutCart {

    private final Cart cart;
    private Delivery delivery;
    private Payment payment;

    CheckoutCart(Cart cart) {
        this.cart = cart;
    }

    CheckoutCart updateDelivery(Delivery delivery) {
        this.delivery = delivery;
        return this;
    }

    CheckoutCart updatePayment(Payment payment) {
        this.payment = payment;
        return this;
    }

    Order order(){
        if(delivery == null){
            throw new MissingDeliveryInCheckoutCartException();
        } else if(payment == null){
            throw new MissingPaymentInCheckoutCartException();
        }
        return new Order(this);
    }
}
