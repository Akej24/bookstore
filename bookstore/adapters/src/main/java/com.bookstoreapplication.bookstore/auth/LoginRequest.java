package com.bookstoreapplication.bookstore.auth;

import com.bookstoreapplication.bookstore.user.value_objects.Password;
import com.bookstoreapplication.bookstore.user.value_objects.UserEmail;
import lombok.AllArgsConstructor;
import lombok.Getter;

import javax.validation.Valid;

@Getter
@AllArgsConstructor
class LoginRequest {

    @Valid
    private UserEmail email;
    @Valid
    private Password password;

}
