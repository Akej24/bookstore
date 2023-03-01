package com.bookstoreapplication.bookstore.user.account;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Getter
@AllArgsConstructor
@NoArgsConstructor
class UserRequest {

    @NotBlank(message="Username must not be blank")
    private String username;

    @NotBlank(message="Password must not be blank")
    private String password;

    @NotBlank(message="Name must not be blank")
    private  String name;

    @NotBlank(message="Surname must not be blank")
    private String surname;

    @NotNull(message = "Date of birth must not be null")
    private LocalDate dateOfBirth;

    @NotNull(message = "Role must not be null")
    private UserRole role;

}

