package com.bookstoreapplication.bookstore.order;

import com.bookstoreapplication.bookstore.order.value_object.PhoneNumber;
import com.bookstoreapplication.bookstore.user.value_objects.FirstName;
import com.bookstoreapplication.bookstore.user.value_objects.LastName;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.Embeddable;

@Getter
@Embeddable
@NoArgsConstructor
@AllArgsConstructor
class ReceiveData {

    private FirstName firstName;
    private LastName lastName;
    private PhoneNumber phoneNumber;

}
