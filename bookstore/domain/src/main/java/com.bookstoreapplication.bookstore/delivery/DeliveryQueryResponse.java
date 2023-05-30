package com.bookstoreapplication.bookstore.delivery;

import com.bookstoreapplication.bookstore.delivery.value_object.DeliveryMethod;
import com.bookstoreapplication.bookstore.delivery.value_object.DeliveryStatus;
import com.bookstoreapplication.bookstore.delivery.value_object.ReceiveDate;
import com.bookstoreapplication.bookstore.delivery.value_object.SendDate;
import com.bookstoreapplication.bookstore.purchase.checkout_cart.Address;
import com.bookstoreapplication.bookstore.purchase.value_object.*;
import com.bookstoreapplication.bookstore.user.value_objects.FirstName;
import com.bookstoreapplication.bookstore.user.value_objects.LastName;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Getter
@AllArgsConstructor(access = AccessLevel.PRIVATE)
class DeliveryQueryResponse {

    private final UUID orderId;
    private final UUID deliveryNumber;
    private final DeliveryMethod deliveryMethod;
    private final AddressQueryResponse address;
    private final DeliveryStatus deliveryStatus;
    private final SendDate sendDate;
    private final ReceiveDate receiveDate;

    static List<DeliveryQueryResponse> from(List<Delivery> sources){
        return sources.stream().map(DeliveryQueryResponse::from).collect(Collectors.toList());
    }

    static DeliveryQueryResponse from(Delivery source){
        return new DeliveryQueryResponse(
                source.getOrderId(),
                source.getDeliveryNumber(),
                source.getDeliveryMethod(),
                AddressQueryResponse.from(source.getAddress()),
                source.getDeliveryStatus(),
                source.getSendDate(),
                source.getReceiveDate()
        );
    }

    @Getter
    @AllArgsConstructor(access = AccessLevel.PRIVATE)
    static class AddressQueryResponse {

        private final FirstName firstName;
        private final LastName lastName;
        private final PhoneNumber phoneNumber;
        private final Street street;
        private final StreetNumber streetNumber;
        private final ZipCode zipCode;
        private final City city;

        static AddressQueryResponse from(Address source) {
            return new AddressQueryResponse(
                    source.getFirstName(),
                    source.getLastName(),
                    source.getPhoneNumber(),
                    source.getStreet(),
                    source.getStreetNumber(),
                    source.getZipCode(),
                    source.getCity()
            );
        }
    }
}
