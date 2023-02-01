package com.bookstoreapplication.bookstore.user.account;

import com.bookstoreapplication.bookstore.purchase.Purchase;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.*;
import org.hibernate.annotations.ColumnDefault;
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
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class UserDatabaseModel implements UserDetails {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long userId;
    @OneToMany(mappedBy = "userDatabaseModelId")
    @Column(name = "purchaseId")
    private Set<Purchase> purchases;
    ///
    @NotBlank
    private String email;
    @NotBlank
    private String username;
    @NotBlank
    private String password;
    @NotBlank
    private String name;
    @NotBlank
    private String surname;
    @NotNull
    private LocalDate dateOfBirth;
    @NotNull
    @Enumerated(EnumType.STRING)
    private UserRole role;
    ///
    @NotNull
    private boolean locked;
    @NotNull
    private boolean enabled;
    ///
    @ColumnDefault("0")
    private double availableFunds;
    @ColumnDefault("0")
    private int purchasedBooks;
    ///
    @Embedded
    private UserAudit userAudit = new UserAudit();

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
