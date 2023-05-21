package com.bookstoreapplication.bookstore.order;

import com.bookstoreapplication.bookstore.order.value_object.*;
import lombok.*;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.UUID;

@Table(name = "orders")
@Entity
@Getter
@NoArgsConstructor
@AllArgsConstructor
class Order implements Serializable {

    @Id
    private UUID orderId;
    private long customerId;
    @Enumerated(EnumType.STRING)
    @NotNull(message = "Payment method cannot be null")
    private PaymentMethod paymentMethod;
    @Embedded
    private OrderDate orderDate;
    @Enumerated(EnumType.STRING)
    @NotNull(message = "Order status cannot be null")
    private OrderStatus orderStatus;

    Order(CheckoutCart checkoutCart) {
        orderId = UUID.randomUUID();
        customerId = checkoutCart.getCartId();
        paymentMethod = checkoutCart.getPaymentMethod();
        orderDate = new OrderDate(LocalDateTime.now());
        orderStatus = OrderStatus.INITIALIZED;
    }

}
