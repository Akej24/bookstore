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
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long orderId;
    private UUID orderNumber;
    private long customerId;
    @OneToOne
    @JoinColumn(name = "delivery_details_delivery_id")
    @AttributeOverride(name = "deliveryId", column = @Column(name = "delivery_id"))
    private DeliveryDetails deliveryDetails;
    @Enumerated(EnumType.STRING)
    @NotNull(message = "Payment method cannot be null")
    private PaymentMethod paymentMethod;
    @Embedded
    private OrderDate orderDate;
    @Enumerated(EnumType.STRING)
    @NotNull(message = "Order status cannot be null")
    private OrderStatus orderStatus;

    public void setDeliveryDetails(DeliveryDetails deliveryDetails) {
        this.deliveryDetails = deliveryDetails;
    }

    Order(CheckoutCart checkoutCart) {
        orderNumber = UUID.randomUUID();
        customerId = checkoutCart.getCart().getCustomerId();
        deliveryDetails = checkoutCart.getDeliveryDetails();
        paymentMethod = checkoutCart.getPaymentMethod();
        orderDate = new OrderDate(LocalDateTime.now());
        orderStatus = OrderStatus.INITIALIZED;
    }

}
