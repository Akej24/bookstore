package com.bookstoreapplication.bookstore.user.registration;

import com.bookstoreapplication.bookstore.user.account.UserDatabaseModel;
import com.bookstoreapplication.bookstore.user.account.UserRole;
import jakarta.persistence.Id;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;
import lombok.Value;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.time.LocalDate;
import java.util.Collection;
import java.util.Collections;

@Getter
@Setter
@Value
public class UserRegistrationModel implements UserDetails {
    @Id
    long userId;
    @NotNull
    String email;
    @NotNull
    String username;
    @NotNull
    String password;
    @NotNull
    String name;
    @NotNull
    String surname;
    @NotNull
    LocalDate dateOfBirth;
    @NotNull
    UserRole role;
    @NotNull
    boolean locked;
    @NotNull
    boolean enabled;

    public UserDatabaseModel toUserDatabaseModel() {
        UserDatabaseModel result = new UserDatabaseModel();
        result.setEmail(this.getEmail());
        result.setUsername(this.getUsername());
        result.setPassword(this.getPassword());
        result.setName(this.getName());
        result.setSurname(this.getSurname());
        result.setDateOfBirth(this.getDateOfBirth());
        result.setRole(this.getRole());
        return result;
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        SimpleGrantedAuthority authority = new SimpleGrantedAuthority(role.name());
        return Collections.singletonList(authority);
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return !locked;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return enabled;
    }
}
