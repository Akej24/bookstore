package com.bookstoreapplication.bookstore.user;

import com.bookstoreapplication.bookstore.purchase.query.SimplePurchaseQueryDto;
import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import javax.persistence.*;
import javax.validation.constraints.DecimalMin;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.io.Serializable;
import java.util.Collection;
import java.util.List;
import java.util.Set;

@Entity
@Table(name = "users")
@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class SimpleUserQueryDto implements UserDetails, Serializable {
    @Id
    private long userId;

    @JsonIgnore
    @OneToMany(mappedBy = "user")
    private Set<SimplePurchaseQueryDto> purchases;

    @Email(message = "Invalid e-mail format")
    private String email;

    @NotBlank(message="Username must not be blank")
    private String username;

    @NotBlank(message="Password must not be blank")
    private String password;

    @Enumerated(EnumType.STRING)
    @NotNull(message = "Role must not be null")
    private UserRole role;

    @DecimalMin(value = "0.0", message = "Minimum value of available funds cannot be less than 0.0")
    @NotNull(message = "Available funds must be not null")
    @Builder.Default
    private Double availableFunds = 0.0;

    @NotNull
    @JsonIgnore
    @Builder.Default
    private Boolean locked = false;

    @NotNull
    @JsonIgnore
    @Builder.Default
    private Boolean enabled = true;

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return List.of(new SimpleGrantedAuthority(role.name()));
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
