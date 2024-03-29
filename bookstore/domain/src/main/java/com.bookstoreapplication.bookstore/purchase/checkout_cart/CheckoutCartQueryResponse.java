package com.bookstoreapplication.bookstore.purchase.checkout_cart;

import com.bookstoreapplication.bookstore.purchase.value_object.City;
import com.bookstoreapplication.bookstore.purchase.value_object.PaymentMethod;
import com.bookstoreapplication.bookstore.purchase.value_object.PhoneNumber;
import com.bookstoreapplication.bookstore.purchase.value_object.Street;
import com.bookstoreapplication.bookstore.purchase.value_object.StreetNumber;
import com.bookstoreapplication.bookstore.purchase.value_object.ZipCode;
import com.bookstoreapplication.bookstore.user.value_objects.FirstName;
import com.bookstoreapplication.bookstore.user.value_objects.LastName;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor(access = AccessLevel.PRIVATE)
class CheckoutCartQueryResponse {

    private final AddressQueryResponse address;
    private final PaymentMethod paymentMethod;

    static CheckoutCartQueryResponse from(CheckoutCart source) {
        return new CheckoutCartQueryResponse(
                source.getAddress() != null ? AddressQueryResponse.from(source.getAddress()) : null,
                source.getPaymentMethod() != null ? source.getPaymentMethod() : null
        );
    }

    @Getter
    @AllArgsConstructor(access = AccessLevel.PRIVATE)
    static class AddressQueryResponse{

        private final FirstName firstName;
        private final LastName lastName;
        private final PhoneNumber phoneNumber;
        private final Street street;
        private final StreetNumber streetNumber;
        private final ZipCode zipCode;
        private final City city;

        static AddressQueryResponse from(Address source){
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
