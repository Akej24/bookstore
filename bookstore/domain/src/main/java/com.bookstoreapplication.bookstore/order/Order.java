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
    private DeliveryDetails deliveryDetails;
    private PaymentMethod paymentMethod;
    private PurchaseDate purchaseDate;
    private PurchaseStatus purchaseStatus;

    Order(CheckoutCart checkoutCart) {
        delegateToPaymentService();
        updateBooksProductsAmount();
        notifyDeliveryService();
        orderNumber = UUID.randomUUID();
        customerId = checkoutCart.getCart().getCustomerId();
        deliveryDetails = checkoutCart.getDeliveryDetails();
        paymentMethod = checkoutCart.getPaymentMethod();
        purchaseDate = new PurchaseDate(LocalDateTime.now());
        purchaseStatus = PurchaseStatus.INITIALIZED;
    }

    private void notifyDeliveryService() {
        //...
    }

    private void updateBooksProductsAmount() {
        //communicate to book facade
    }

    private void delegateToPaymentService() {
        //...
    }

}
