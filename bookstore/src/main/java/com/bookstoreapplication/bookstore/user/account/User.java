package com.bookstoreapplication.bookstore.user.account;

import com.bookstoreapplication.bookstore.purchase.PurchaseDatabaseModel;
import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import jakarta.validation.constraints.*;
import lombok.*;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.time.LocalDate;
import java.util.Collection;
import java.util.List;
import java.util.Set;

@Entity
@Table(name = "users")
@Getter
@AllArgsConstructor
@NoArgsConstructor
@Builder(toBuilder = true)
public class User implements UserDetails {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long userId;

    @OneToMany(mappedBy = "user")
    @Column(name = "purchaseId")
    @JsonIgnore
    private Set<PurchaseDatabaseModel> purchaseDatabaseModels;

    @Email(message = "Invalid e-mail format")
    private String email;

    @NotBlank(message="Username must not be blank")
    private String username;

    @NotBlank(message="Password must not be blank")
    private String password;

    @NotBlank(message="Name must not be blank")
    private  String name;

    @NotBlank(message="Surname must not be blank")
    private String surname;

    @NotNull(message = "Date of birth must not be null")
    private LocalDate dateOfBirth;

    @NotNull(message = "Role must not be null")
    private UserRole role;

    @DecimalMin(value = "0.0", message = "Minimum value of available funds cannot be less than 0.0")
    @NotNull
    private Double availableFunds = 0.0;

    @Min(value = 0, message = "Minimum value of purchased books cannot be less than 0")
    @NotNull
    private Integer purchasedBooks = 0;

    @Embedded
    @JsonIgnore
    private UserAudit userAudit;

    @NotNull
    @JsonIgnore
    private Boolean locked = false;

    @NotNull
    @JsonIgnore
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
