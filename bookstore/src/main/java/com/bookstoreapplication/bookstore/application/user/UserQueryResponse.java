package com.bookstoreapplication.bookstore.domain.user;

import com.bookstoreapplication.bookstore.domain.purchase.value_object.Funds;
import com.bookstoreapplication.bookstore.domain.user.value_objects.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

import java.time.LocalDate;

@Getter
@AllArgsConstructor
public class UserQueryResponse {

    private UserEmail userEmail;
    private Username username;
    private Password password;
    private FirstName firstName;
    private LastName lastName;
    private DateOfBirth dateOfBirth;
    private Funds funds;

}
