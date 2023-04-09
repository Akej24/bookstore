package com.bookstoreapplication.bookstore.application.user;

import com.bookstoreapplication.bookstore.domain.user.value_objects.*;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class UserUpdateCommand {

    private Username username;
    private Password password;
    private FirstName firstName;
    private LastName lastName;
    private DateOfBirth dateOfBirth;

}