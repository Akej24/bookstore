package com.bookstoreapplication.bookstore.user;

import com.bookstoreapplication.bookstore.user.value_objects.*;
import lombok.AllArgsConstructor;
import lombok.Getter;

import javax.validation.Valid;

@Getter
@AllArgsConstructor
class UserCommand {

    @Valid
    private UserEmail email;
    @Valid
    private Username username;
    @Valid
    private Password password;
    @Valid
    private FirstName firstName;
    @Valid
    private LastName lastName;
    @Valid
    private DateOfBirth dateOfBirth;
    @Valid
    private UserRole role;

}

