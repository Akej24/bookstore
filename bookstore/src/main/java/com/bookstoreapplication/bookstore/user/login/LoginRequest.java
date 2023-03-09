package com.bookstoreapplication.bookstore.user.login;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;

@Getter
@AllArgsConstructor
@NoArgsConstructor
class LoginRequest {

    @Email(message = "Invalid e-mail format")
    @NotBlank(message = "E-mail must not be blank")
    private String email;

    @NotBlank(message = "Password must not be blank")
    private String password;

}
