package com.bookstoreapplication.bookstore.order;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Table(name = "delivery_details")
@Entity
@Getter
@NoArgsConstructor
@AllArgsConstructor
class DeliveryDetails {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long deliveryId;
    @Embedded
    private Address address;
    @Embedded
    private ReceiveData receiveData;

}
