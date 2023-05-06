package com.bookstoreapplication.bookstore.order;

import com.bookstoreapplication.bookstore.order.value_object.*;
import lombok.*;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.UUID;

@Getter
@AllArgsConstructor(access = AccessLevel.PROTECTED)
@NoArgsConstructor
class Order implements Serializable {

    private long orderId;
    private UUID orderNumber;
    private long customerId;
    private Delivery delivery;
    private Payment payment;
    private PurchaseDate purchaseDate;
    private PurchaseStatus purchaseStatus;

    Order(CheckoutCart checkoutCart) {
        orderNumber = UUID.randomUUID();
        customerId = checkoutCart.getCart().getCustomerId();
        delivery = checkoutCart.getDelivery();
        payment = checkoutCart.getPayment();
        purchaseDate = new PurchaseDate(LocalDateTime.now());
        purchaseStatus = PurchaseStatus.INITIALIZED;
    }

}
