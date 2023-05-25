package com.bookstoreapplication.bookstore.purchase.checkout_cart;

import com.bookstoreapplication.bookstore.purchase.value_object.*;
import com.bookstoreapplication.bookstore.user.value_objects.FirstName;
import com.bookstoreapplication.bookstore.user.value_objects.LastName;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.Embeddable;
import javax.persistence.Embedded;

@Getter
@Embeddable
@NoArgsConstructor
@AllArgsConstructor
public class Address {

    @Embedded
    private FirstName firstName;
    @Embedded
    private LastName lastName;
    @Embedded
    private PhoneNumber phoneNumber;
    @Embedded
    private Street street;
    @Embedded
    private StreetNumber streetNumber;
    @Embedded
    private ZipCode zipCode;
    @Embedded
    private City city;

}
