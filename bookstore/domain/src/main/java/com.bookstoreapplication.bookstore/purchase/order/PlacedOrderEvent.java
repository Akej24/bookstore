package com.bookstoreapplication.bookstore.purchase.order;

import com.bookstoreapplication.bookstore.purchase.cart.Cart;
import com.bookstoreapplication.bookstore.purchase.checkout_cart.CheckoutCart;
import lombok.Getter;
import org.springframework.context.ApplicationEvent;

@Getter
class PlacedOrderEvent extends ApplicationEvent {

    private final Cart cart;
    private final CheckoutCart checkoutCart;
    private final Order order;

    public PlacedOrderEvent(Object source, Cart cart, CheckoutCart checkoutCart, Order order) {
        super(source);
        this.cart = cart;
        this.checkoutCart = checkoutCart;
        this.order = order;
    }
}
