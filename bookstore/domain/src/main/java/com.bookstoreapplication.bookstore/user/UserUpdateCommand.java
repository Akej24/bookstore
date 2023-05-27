package com.bookstoreapplication.bookstore.user;

import com.bookstoreapplication.bookstore.user.value_objects.*;
import lombok.AllArgsConstructor;
import lombok.Getter;

import javax.validation.Valid;

record UserUpdateCommand(

        @Valid Username username,
        @Valid Password password,
        @Valid FirstName firstName,
        @Valid LastName lastName,
        @Valid DateOfBirth dateOfBirth

) { }
