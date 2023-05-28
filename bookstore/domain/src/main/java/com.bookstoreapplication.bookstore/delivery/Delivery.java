package com.bookstoreapplication.bookstore.delivery;

import com.bookstoreapplication.bookstore.delivery.value_object.DeliveryMethod;
import com.bookstoreapplication.bookstore.delivery.value_object.DeliveryStatus;
import com.bookstoreapplication.bookstore.delivery.value_object.ReceiveDate;
import com.bookstoreapplication.bookstore.delivery.value_object.SendDate;
import com.bookstoreapplication.bookstore.purchase.checkout_cart.Address;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.UUID;

@Table(name = "deliveries")
@Entity
@Getter
@AllArgsConstructor(access = AccessLevel.PROTECTED)
@NoArgsConstructor
class Delivery implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long deliveryId;
    private UUID orderId;
    private UUID deliveryNumber;
    @Enumerated(EnumType.STRING)
    @NotNull(message = "Delivery method cannot be null")
    private DeliveryMethod deliveryMethod;
    @Embedded
    private Address address;
    @Enumerated(EnumType.STRING)
    @NotNull(message = "Delivery status cannot be null")
    private DeliveryStatus deliveryStatus;
    @Embedded
    private SendDate sendDate;
    @Embedded
    private ReceiveDate receiveDate;

    private Delivery(UUID orderId, Address address) {
        this.orderId = orderId;
        this.deliveryNumber = UUID.randomUUID();
        this.deliveryMethod = DeliveryMethod.COURIER;
        this.address = address;
        this.deliveryStatus = DeliveryStatus.PREPARE;
    }

    static Delivery from(UUID orderId, Address address){
        return new Delivery(orderId, address);
    }

    Delivery markSend() {
        this.deliveryStatus = DeliveryStatus.SEND;
        this.sendDate = new SendDate(LocalDateTime.now());
        return this;
    }

//    Delivery markReceived() {
//        this.receiveDate = new ReceiveDate(LocalDateTime.now());
//        this.deliveryStatus = DeliveryStatus.RECEIVED;
//        return this;
//    }
}
