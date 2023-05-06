package com.bookstoreapplication.bookstore.purchase;

import com.bookstoreapplication.bookstore.purchase.value_object.*;
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
