package com.bookstoreapplication.bookstore.purchase.checkout_cart;

import com.bookstoreapplication.bookstore.purchase.value_object.*;
import com.bookstoreapplication.bookstore.user.value_objects.FirstName;
import com.bookstoreapplication.bookstore.user.value_objects.LastName;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonUnwrapped;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor(access = AccessLevel.PRIVATE)
class CheckoutCartJsonQueryResponse {

    @JsonInclude(JsonInclude.Include.NON_NULL)
    private final AddressJsonQueryResponse address;

    @JsonUnwrapped
    @JsonInclude(JsonInclude.Include.NON_NULL)
    private final PaymentMethod paymentMethod;

    static CheckoutCartJsonQueryResponse from(CheckoutCartQueryResponse source) {
        return new CheckoutCartJsonQueryResponse(
                source.getAddress() != null ? AddressJsonQueryResponse.from(source.getAddress()) : null,
                source.getPaymentMethod() != null ? source.getPaymentMethod() : null
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

        static AddressJsonQueryResponse from(CheckoutCartQueryResponse.AddressQueryResponse source){
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
