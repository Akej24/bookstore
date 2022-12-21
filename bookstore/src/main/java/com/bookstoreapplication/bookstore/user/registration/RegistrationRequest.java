package com.bookstoreapplication.bookstore.user.registration;

import com.bookstoreapplication.bookstore.user.account.UserRole;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Getter;

import java.time.LocalDate;

@Getter
@AllArgsConstructor
public class RegistrationRequest {
    @NotNull
    private final String email;
    @NotNull
    private final String username;
    @NotNull
    private final String password;
    @NotNull
    private final String name;
    @NotNull
    private final String surname;
    @NotNull
    private final LocalDate dateOfBirth;
    @NotNull
    private final UserRole role;
    @NotNull
    private final boolean locked;
    @NotNull
    private final boolean enabled;
}
