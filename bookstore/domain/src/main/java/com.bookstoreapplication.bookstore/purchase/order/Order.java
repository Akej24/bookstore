package com.bookstoreapplication.bookstore.purchase.order;

import com.bookstoreapplication.bookstore.purchase.checkout_cart.CheckoutCart;
import com.bookstoreapplication.bookstore.purchase.value_object.*;
import lombok.*;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.UUID;

@Table(name = "orders")
@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
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

    private Order(CheckoutCart checkoutCart) {
        checkoutCart.checkEnoughDataToPayAndDeliver();
        orderId = UUID.randomUUID();
        customerId = checkoutCart.getCartId();
        paymentMethod = checkoutCart.getPaymentMethod();
        orderDate = new OrderDate(LocalDateTime.now());
        orderStatus = OrderStatus.INITIALIZED;
    }

    static Order from(CheckoutCart source){
        return new Order(source);
    }
}
