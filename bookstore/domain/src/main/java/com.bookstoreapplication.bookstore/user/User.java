package com.bookstoreapplication.bookstore.user;

import com.bookstoreapplication.bookstore.purchase.value_object.Funds;
import com.bookstoreapplication.bookstore.user.value_objects.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import javax.validation.constraints.*;
import java.io.Serializable;
import java.math.BigDecimal;

@Table(name = "users")
@Entity
@Getter
@AllArgsConstructor
@NoArgsConstructor
class User implements Serializable {
    @EmbeddedId
    private UserId userId;
    @Embedded
    private UserEmail email;
    @Embedded
    private Username username;
    @Embedded
    private EncodedPassword encodedPassword;
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

    public User(UserCommand source, String encodedPasswordFormat) {
        email = source.getEmail();
        username = source.getUsername();
        encodedPassword = source.getPassword().toEncodedFormat(encodedPasswordFormat);
        firstName = source.getFirstName();
        lastName = source.getLastName();
        dateOfBirth = source.getDateOfBirth();
        funds = new Funds(BigDecimal.ZERO);
        role = source.getRole();
        userAudit = new UserAudit();
        locked = new Locked(false);
        enabled = new Enabled(true);
    }

    public User update(UserUpdateCommand source, String encodedPasswordFormat){
        username = source.getUsername();
        encodedPassword = source.getPassword().toEncodedFormat(encodedPasswordFormat);
        firstName = source.getFirstName();
        lastName = source.getLastName();
        dateOfBirth = source.getDateOfBirth();
        return this;
    }

}
