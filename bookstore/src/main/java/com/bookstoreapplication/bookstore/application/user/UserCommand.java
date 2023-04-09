package com.bookstoreapplication.bookstore.domain.user;

import com.bookstoreapplication.bookstore.domain.user.value_objects.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.time.LocalDate;

@Getter
@AllArgsConstructor
class UserCommand {

    private UserEmail userEmail;
    private Username username;
    private Password password;
    private FirstName firstName;
    private LastName lastName;
    private DateOfBirth dateOfBirth;
    private UserRole userRole;

}

