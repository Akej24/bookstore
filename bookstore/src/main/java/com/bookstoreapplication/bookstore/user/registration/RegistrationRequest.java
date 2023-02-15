package com.bookstoreapplication.bookstore.user.registration;

import com.bookstoreapplication.bookstore.user.account.UserRole;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
class RegistrationRequest {

    @NotNull(message="Email must be not null")
    private String email;
    @NotNull(message="Username must be not null")
    private String username;
    @NotNull(message="Password must be not null")
    private String password;
    @NotNull(message="Name must be not null")
    private  String name;
    @NotNull(message="Surname must be not null")
    private String surname;
    @NotNull
    private LocalDate dateOfBirth;
    @NotNull
    private UserRole role;

}
