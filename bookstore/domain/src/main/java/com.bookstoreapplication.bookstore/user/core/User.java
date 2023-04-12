package com.bookstoreapplication.bookstore.user.core;

import com.bookstoreapplication.bookstore.user.UserCommand;
import com.bookstoreapplication.bookstore.user.UserUpdateCommand;
import com.bookstoreapplication.bookstore.purchase.value_object.Funds;
import com.bookstoreapplication.bookstore.user.value_objects.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import javax.persistence.*;
import javax.validation.constraints.*;
import java.io.Serializable;
import java.math.BigDecimal;

@Table(name = "users")
@Entity
@Getter
@AllArgsConstructor
@NoArgsConstructor
public class User implements Serializable {
    @EmbeddedId
    private UserId userId;
    @Embedded
    private UserEmail email;
    @Embedded
    private Username username;
    @Embedded
    private Password password;
    @Embedded
    private FirstName firstName;
    @Embedded
    private LastName lastName;
    @Embedded
    private DateOfBirth dateOfBirth;
    @Embedded
    private Funds funds;
    @Enumerated(EnumType.STRING)
    @NotNull(message = "Role must not be null")
    private UserRole role;
    @Embedded
    private UserAudit userAudit;
    @Embedded
    private Locked locked;
    @Embedded
    private Enabled enabled;

    public User(UserCommand source, BCryptPasswordEncoder bCryptPasswordEncoder) {
        email = source.getUserEmail();
        username = source.getUsername();
        password = new Password(bCryptPasswordEncoder.encode(source.getPassword().getPassword()));
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
