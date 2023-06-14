package com.bookstoreapplication.bookstore.purchase.checkout_cart;

import com.bookstoreapplication.bookstore.purchase.value_object.*;
import com.bookstoreapplication.bookstore.user.value_objects.FirstName;
import com.bookstoreapplication.bookstore.user.value_objects.LastName;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.validation.annotation.Validated;

import javax.persistence.Embeddable;
import javax.persistence.Embedded;
import javax.validation.Valid;

@Getter
@Validated
@Embeddable
@NoArgsConstructor
@AllArgsConstructor
public class Address {

    @Valid @Embedded private FirstName firstName;
    @Valid @Embedded private LastName lastName;
    @Valid @Embedded private PhoneNumber phoneNumber;
    @Valid @Embedded private Street street;
    @Valid @Embedded private StreetNumber streetNumber;
    @Valid @Embedded private ZipCode zipCode;
    @Valid @Embedded private City city;

}
