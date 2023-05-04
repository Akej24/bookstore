package com.bookstoreapplication.bookstore.auth;

import com.bookstoreapplication.bookstore.user.value_objects.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.io.Serializable;
import java.util.Collection;
import java.util.List;

@Entity
@Table(name = "users")
@Getter
@NoArgsConstructor
class SecuredUser implements UserDetails, Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long userId;
    @Embedded
    private UserEmail userEmail;
    @Embedded
    private EncodedPassword encodedPassword;
    @Enumerated(EnumType.STRING)
    @NotNull(message = "Role must not be null")
    private UserRole role;
    @Embedded
    private Locked locked;
    @Embedded
    private Enabled enabled;

    void checkPasswordsMatch(Password requestPassword, BCryptPasswordEncoder bCryptPasswordEncoder) {
        if(!bCryptPasswordEncoder.matches(requestPassword.getPassword(), encodedPassword.getEncodedPassword())){
            throw new IllegalArgumentException("Invalid password for user with given e-mail");
        }
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return List.of(new SimpleGrantedAuthority(role.name()));
    }

    @Override
    public String getPassword() { return encodedPassword.getEncodedPassword(); }

    @Override
    public String getUsername() { return userEmail.getEmail(); }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return !locked.getLocked();
    }

    @Override
    public boolean isEnabled() {
        return enabled.getEnabled();
    }
}
