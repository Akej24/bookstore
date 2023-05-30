package com.bookstoreapplication.bookstore.delivery;

import com.bookstoreapplication.bookstore.delivery.value_object.DeliveryMethod;
import com.bookstoreapplication.bookstore.delivery.value_object.DeliveryStatus;
import com.bookstoreapplication.bookstore.delivery.value_object.ReceiveDate;
import com.bookstoreapplication.bookstore.delivery.value_object.SendDate;
import com.bookstoreapplication.bookstore.purchase.value_object.*;
import com.bookstoreapplication.bookstore.user.value_objects.FirstName;
import com.bookstoreapplication.bookstore.user.value_objects.LastName;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonUnwrapped;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Getter
@AllArgsConstructor(access = AccessLevel.PRIVATE)
class DeliveryJsonQueryResponse {

    private final UUID orderId;
    private final UUID deliveryNumber;
    private final DeliveryMethod deliveryMethod;
    private final AddressJsonQueryResponse address;
    private final DeliveryStatus deliveryStatus;

    @JsonInclude(JsonInclude.Include.NON_NULL)
    @JsonUnwrapped
    private final SendDate sendDate;

    @JsonInclude(JsonInclude.Include.NON_NULL)
    @JsonUnwrapped
    private final ReceiveDate receiveDate;

    static List<DeliveryJsonQueryResponse> from(List<DeliveryQueryResponse> sources){
        return sources.stream()
                .map(DeliveryJsonQueryResponse::from)
                .collect(Collectors.toList());
    }

    static DeliveryJsonQueryResponse from(DeliveryQueryResponse source) {
        return new DeliveryJsonQueryResponse(
                source.getOrderId(),
                source.getDeliveryNumber(),
                source.getDeliveryMethod(),
                AddressJsonQueryResponse.from(source.getAddress()),
                source.getDeliveryStatus(),
                source.getSendDate(),
                source.getReceiveDate()
        );
    }

    @Getter
    @AllArgsConstructor(access = AccessLevel.PRIVATE)
    static class AddressJsonQueryResponse{

        @JsonUnwrapped
        private final FirstName firstName;
        @JsonUnwrapped
        private final LastName lastName;
        @JsonUnwrapped
        private final PhoneNumber phoneNumber;
        @JsonUnwrapped
        private final Street street;
        @JsonUnwrapped
        private final StreetNumber streetNumber;
        @JsonUnwrapped
        private final ZipCode zipCode;
        @JsonUnwrapped
        private final City city;

        static AddressJsonQueryResponse from(DeliveryQueryResponse.AddressQueryResponse source){
            return new AddressJsonQueryResponse(
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
