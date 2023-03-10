package com.bookstoreapplication.bookstore.user.registration;

import com.bookstoreapplication.bookstore.user.account.UserRole;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.time.LocalDate;

@Getter
@AllArgsConstructor
@NoArgsConstructor
class RegistrationRequest {

    @NotBlank(message = "E-mail must not be blank")
    @Email(message = "Invalid e-mail format")
    private String email;

    @NotBlank(message = "Username must not be blank")
    private String username;

    @NotBlank(message = "Password must not be blank")
    private String password;

    @NotBlank(message = "Name must not be blank")
    private  String name;

    @NotBlank(message="Surname must not be blank")
    private String surname;

    @NotNull(message = "Date of birth must not be null")
    private LocalDate dateOfBirth;

    @NotNull(message = "Role must not be null")
    private UserRole role;

}
