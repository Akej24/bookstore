package com.bookstoreapplication.bookstore.user.registration;

import com.bookstoreapplication.bookstore.user.account.UserRole;
import jakarta.validation.constraints.NotNull;

import java.time.LocalDate;
record RegistrationRequest(
        @NotNull String email, @NotNull String username, @NotNull String password,
        @NotNull String name, @NotNull String surname, @NotNull LocalDate dateOfBirth,
        @NotNull UserRole role, @NotNull boolean locked, @NotNull boolean enabled) {
}
