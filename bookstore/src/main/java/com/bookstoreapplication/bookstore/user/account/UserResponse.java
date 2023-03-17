package com.bookstoreapplication.bookstore.user.account;

import lombok.Builder;
import lombok.Getter;

import java.time.LocalDate;

@Getter
@Builder
class UserResponse {

    private String email;
    private String username;
    private String password;
    private String name;
    private String surname;
    private LocalDate dateOfBirth;
    private UserRole role;
    private Double availableFunds;

}
