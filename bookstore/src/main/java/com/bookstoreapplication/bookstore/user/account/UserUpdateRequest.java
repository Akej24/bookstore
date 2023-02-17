package com.bookstoreapplication.bookstore.user.account;

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
class UserUpdateRequest {

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

}

