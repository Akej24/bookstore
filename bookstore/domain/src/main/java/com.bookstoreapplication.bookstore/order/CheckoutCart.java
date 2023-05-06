package com.bookstoreapplication.bookstore.order;

import com.bookstoreapplication.bookstore.order.exception.MissingDeliveryInCheckoutCartException;
import com.bookstoreapplication.bookstore.order.exception.MissingPaymentInCheckoutCartException;
import lombok.Getter;

@Getter
class CheckoutCart {

    private final Cart cart;
    private DeliveryDetails deliveryDetails;
    private PaymentMethod paymentMethod;

    CheckoutCart(Cart cart) {
        this.cart = cart;
    }

    CheckoutCart updateDelivery(DeliveryDetails deliveryDetails) {
        this.deliveryDetails = deliveryDetails;
        return this;
    }

    CheckoutCart updatePayment(PaymentMethod paymentMethod) {
        this.paymentMethod = paymentMethod;
        return this;
    }

    Order order(){
        if(deliveryDetails == null){
            throw new MissingDeliveryInCheckoutCartException();
        } else if(paymentMethod == null){
            throw new MissingPaymentInCheckoutCartException();
        }
        return new Order(this);
    }
}
