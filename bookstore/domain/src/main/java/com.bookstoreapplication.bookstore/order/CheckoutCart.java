package com.bookstoreapplication.bookstore.order;

import com.bookstoreapplication.bookstore.order.exception.*;
import com.bookstoreapplication.bookstore.order.value_object.PaymentMethod;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
class CheckoutCart {

    private final long cartId;
    private Address address;
    private PaymentMethod paymentMethod;

    CheckoutCart(Cart cart) {
        this.cartId = cart.getCustomerId();
    }

    CheckoutCart updateAddress(Address address) {
        this.address = address;
        return this;
    }

    CheckoutCart updatePaymentMethod(PaymentMethod paymentMethod) {
        this.paymentMethod = paymentMethod;
        return this;
    }

    boolean hasEnoughDataToPayAndDeliver(){
        if(paymentMethod == null) {
             throw new MissingPaymentMethodInCheckoutCartException();
        } else if(address == null) {
            throw new MissingDeliveryDetailsInCheckoutCartException();
        }
        return true;
    }

    Order placeOrder() {
        return new Order(this);
    }

}
