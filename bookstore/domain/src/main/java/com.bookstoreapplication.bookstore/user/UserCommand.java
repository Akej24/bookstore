package com.bookstoreapplication.bookstore.user;

import com.bookstoreapplication.bookstore.user.value_objects.*;

import javax.validation.Valid;

record UserCommand(

        @Valid UserEmail email,
        @Valid Username username,
        @Valid Password password,
        @Valid FirstName firstName,
        @Valid LastName lastName,
        @Valid DateOfBirth dateOfBirth,
        @Valid UserRole role

) { }

