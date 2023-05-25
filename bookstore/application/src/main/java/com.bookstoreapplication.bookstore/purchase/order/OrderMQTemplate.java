package com.bookstoreapplication.bookstore.purchase.order;

import com.bookstoreapplication.bookstore.purchase.cart.Cart;
import com.bookstoreapplication.bookstore.purchase.checkout_cart.Address;
import com.bookstoreapplication.bookstore.purchase.checkout_cart.CheckoutCart;

import java.util.List;
import java.util.UUID;

interface OrderMQTemplate {

    void publishPayment(Cart cart, CheckoutCart customerCheckoutCart, UUID orderId);

    void publishBooksDecrement(List<OrderDetail> orderDetails);

    void publishDelivery(UUID orderId, Address address);

}
