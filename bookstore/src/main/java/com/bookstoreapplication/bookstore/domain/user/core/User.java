package com.bookstoreapplication.bookstore.domain.user;

import com.bookstoreapplication.bookstore.application.user.UserCommand;
import com.bookstoreapplication.bookstore.application.user.UserUpdateCommand;
import com.bookstoreapplication.bookstore.domain.purchase.value_object.Funds;
import com.bookstoreapplication.bookstore.domain.user.value_objects.*;
import lombok.AllArgsConstructor;
import lombok.Getter;

import javax.persistence.*;
import javax.validation.constraints.*;
import java.io.Serializable;
import java.math.BigDecimal;

@Getter
@AllArgsConstructor
public class User implements Serializable {
    @Id
    private UserId userId;
    private UserEmail email;
    private Username username;
    private Password password;
    private FirstName firstName;
    private LastName lastName;
    private DateOfBirth dateOfBirth;
    private Funds funds;
    @Enumerated(EnumType.STRING)
    @NotNull(message = "Role must not be null")
    private UserRole role;
    @Embedded
    private UserAudit userAudit;
    private Locked locked;
    private Enabled enabled;

    public User(UserCommand source) {
        email = source.getUserEmail();
        username = source.getUsername();
        password = source.getPassword();
        firstName = source.getFirstName();
        lastName = source.getLastName();
        dateOfBirth = source.getDateOfBirth();
        funds = new Funds(BigDecimal.ZERO);
        role = source.getUserRole();
        userAudit = new UserAudit();
        locked = new Locked(false);
        enabled = new Enabled(true);
    }

    public User update(UserUpdateCommand source){
        username = source.getUsername();
        password = source.getPassword();
        firstName = source.getFirstName();
        lastName = source.getLastName();
        dateOfBirth = source.getDateOfBirth();
        return this;
    }

}
