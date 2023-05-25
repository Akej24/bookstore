package com.bookstoreapplication.bookstore.purchase.checkout_cart;

import com.bookstoreapplication.bookstore.purchase.value_object.City;
import com.bookstoreapplication.bookstore.purchase.value_object.PaymentMethod;
import com.bookstoreapplication.bookstore.purchase.value_object.PhoneNumber;
import com.bookstoreapplication.bookstore.purchase.value_object.Street;
import com.bookstoreapplication.bookstore.purchase.value_object.StreetNumber;
import com.bookstoreapplication.bookstore.purchase.value_object.ZipCode;
import com.bookstoreapplication.bookstore.user.value_objects.FirstName;
import com.bookstoreapplication.bookstore.user.value_objects.LastName;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonUnwrapped;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
class CheckoutCartQueryResponse {

    @JsonInclude(JsonInclude.Include.NON_NULL)
    private AddressQueryResponse address;

    @JsonUnwrapped
    @JsonInclude(JsonInclude.Include.NON_NULL)
    private PaymentMethod paymentMethod;

    static CheckoutCartQueryResponse toResponse(CheckoutCart source) {
        return new CheckoutCartQueryResponse(
                source.getAddress() != null ? AddressQueryResponse.toResponse(source.getAddress()) : null,
                source.getPaymentMethod() != null ? source.getPaymentMethod() : null
        );
    }

    @Getter
    @AllArgsConstructor
    static class AddressQueryResponse{

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

        static AddressQueryResponse toResponse(Address source){
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
