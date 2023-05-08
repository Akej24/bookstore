package com.bookstoreapplication.bookstore.order;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.Embeddable;

@Getter
@Embeddable
@NoArgsConstructor
@AllArgsConstructor
class Address {

    private String street;
    private Integer streetNumber;
    private String zipCode;
    private String city;

}
