package com.bookstoreapplication.bookstore.order;

import java.util.List;
import java.util.UUID;

interface OrderMQTemplate {

    void publishPayment(Cart cart, CheckoutCart customerCheckoutCart, UUID orderId);

    void publishBooksDecrement(List<OrderDetail> orderDetails);

    void publishDelivery(UUID orderId, Address address);

}
