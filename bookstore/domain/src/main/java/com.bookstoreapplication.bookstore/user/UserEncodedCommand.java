package com.bookstoreapplication.bookstore.user;

import com.bookstoreapplication.bookstore.user.value_objects.*;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class UserEncodedCommand {

    private UserEmail userEmail;
    private Username username;
    private EncodedPassword encodedPassword;
    private FirstName firstName;
    private LastName lastName;
    private DateOfBirth dateOfBirth;
    private UserRole userRole;

}
