package com.bookstoreapplication.bookstore.user.login;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@AllArgsConstructor
@NoArgsConstructor
class LoginRequest {

    @Email(message = "Invalid e-mail format")
    private String email;

    @NotBlank(message="Password must not be blank")
    private String password;

}
