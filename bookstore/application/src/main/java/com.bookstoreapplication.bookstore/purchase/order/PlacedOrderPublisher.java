package com.bookstoreapplication.bookstore.purchase.order;

import com.bookstoreapplication.bookstore.purchase.cart.Cart;
import com.bookstoreapplication.bookstore.purchase.checkout_cart.CheckoutCart;
import lombok.AllArgsConstructor;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.stereotype.Component;

@Component
@AllArgsConstructor
class PlacedOrderPublisher {

    private ApplicationEventPublisher applicationEventPublisher;

    public void publishPlacedOrderEvent(Cart cart, CheckoutCart checkoutCart, Order order) {
        PlacedOrderEvent placedOrderEvent = new PlacedOrderEvent(this, cart, checkoutCart, order);
        applicationEventPublisher.publishEvent(placedOrderEvent);
    }
}
