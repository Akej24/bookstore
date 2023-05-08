package com.bookstoreapplication.bookstore.order;

import com.bookstoreapplication.bookstore.order.exception.*;
import com.bookstoreapplication.bookstore.order.value_object.PaymentMethod;
import lombok.Getter;

import java.util.List;

@Getter
class CheckoutCart {

    private final Cart cart;
    private DeliveryDetails deliveryDetails;
    private PaymentMethod paymentMethod;

    CheckoutCart(Cart cart) {
        this.cart = cart;
    }

    CheckoutCart updateDeliveryDetails(DeliveryDetails deliveryDetails) {
        this.deliveryDetails = deliveryDetails;
        return this;
    }

    CheckoutCart updatePaymentMethod(PaymentMethod paymentMethod) {
        this.paymentMethod = paymentMethod;
        return this;
    }

    boolean hasEnoughDataToPayAndDeliver(){
        if(paymentMethod == null) {
             throw new MissingPaymentMethodInCheckoutCartException();
        } else if(deliveryDetails == null) {
            throw new MissingDeliveryDetailsInCheckoutCartException();
        }
        return true;
    }

    List<OrderDetail> mapToOrderDetails(long orderId){
        return this.getCart().getCartLines()
                .stream()
                .map(line -> new OrderDetail(line, orderId))
                .toList();
    }

    Order placeOrder() {
        return new Order(this);
    }

}
