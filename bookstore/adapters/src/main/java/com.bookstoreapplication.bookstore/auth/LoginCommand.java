package com.bookstoreapplication.bookstore.auth;

import com.bookstoreapplication.bookstore.user.value_objects.Password;
import com.bookstoreapplication.bookstore.user.value_objects.UserEmail;

import javax.validation.Valid;

record LoginCommand(

        @Valid UserEmail email,
        @Valid Password password

) { }
