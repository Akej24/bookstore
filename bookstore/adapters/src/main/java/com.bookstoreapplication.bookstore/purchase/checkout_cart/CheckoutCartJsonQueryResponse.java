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
    private AddressJsonQueryResponse address;

    @JsonUnwrapped
    @JsonInclude(JsonInclude.Include.NON_NULL)
    private PaymentMethod paymentMethod;

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
        private FirstName firstName;
        @JsonUnwrapped
        private LastName lastName;
        @JsonUnwrapped
        private PhoneNumber phoneNumber;
        @JsonUnwrapped
        private Street street;
        @JsonUnwrapped
        private StreetNumber streetNumber;
        @JsonUnwrapped
        private ZipCode zipCode;
        @JsonUnwrapped
        private City city;

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
