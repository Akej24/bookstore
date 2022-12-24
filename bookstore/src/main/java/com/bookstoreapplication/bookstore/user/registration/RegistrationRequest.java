package com.bookstoreapplication.bookstore.user.registration;

import com.bookstoreapplication.bookstore.user.account.UserRole;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Getter
@AllArgsConstructor
@NoArgsConstructor
public class RegistrationRequest {

    @NotNull
    private String email;
    @NotNull
    private String username;
    @NotNull
    private String password;
    @NotNull
    private  String name;
    @NotNull
    private String surname;
    @NotNull
    private LocalDate dateOfBirth;
    @NotNull
    private UserRole role;
    @NotNull
    private boolean locked;
    @NotNull
    private boolean enabled;

}
