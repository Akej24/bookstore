package com.bookstoreapplication.bookstore.user;

import com.bookstoreapplication.bookstore.user.value_objects.*;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class UserCommand {

    private UserEmail email;
    private Username username;
    private Password password;
    private FirstName firstName;
    private LastName lastName;
    private DateOfBirth dateOfBirth;
    private UserRole role;

}

