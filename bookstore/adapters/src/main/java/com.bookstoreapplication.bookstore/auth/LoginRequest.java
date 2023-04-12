package com.bookstoreapplication.bookstore.auth;

import com.bookstoreapplication.bookstore.user.value_objects.Password;
import com.bookstoreapplication.bookstore.user.value_objects.UserEmail;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
class LoginRequest {

    private UserEmail email;
    private Password password;

}
