package com.bookstoreapplication.bookstore.purchase.checkout_cart;

import com.bookstoreapplication.bookstore.purchase.cart.Cart;
import com.bookstoreapplication.bookstore.purchase.exception.*;
import com.bookstoreapplication.bookstore.purchase.value_object.PaymentMethod;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class CheckoutCart {

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

    public boolean hasEnoughDataToPayAndDeliver(){
        if(paymentMethod == null) {
             throw new MissingPaymentMethodInCheckoutCartException();
        } else if(address == null) {
            throw new MissingDeliveryDetailsInCheckoutCartException();
        }
        return true;
    }

}
