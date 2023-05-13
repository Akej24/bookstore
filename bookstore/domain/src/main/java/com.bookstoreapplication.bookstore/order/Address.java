package com.bookstoreapplication.bookstore.order;

import com.bookstoreapplication.bookstore.order.value_object.*;
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
class Address {

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
