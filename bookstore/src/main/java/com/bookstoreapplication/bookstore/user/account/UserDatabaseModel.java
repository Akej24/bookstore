package com.bookstoreapplication.bookstore.user.account;

import com.bookstoreapplication.bookstore.purchase.Purchase;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.ColumnDefault;

import java.time.LocalDate;
import java.util.Set;

@Entity
@Table(name = "users")
@Getter
@Setter
public class UserDatabaseModel {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long userId;
    @OneToMany(mappedBy = "userDatabaseModelId")
    @Column(name = "purchaseId")
    private Set<Purchase> purchases;
    ///
    @NotNull
    private String email;
    @NotNull
    private String username;
    @NotNull
    private String password;
    @NotNull
    private String name;
    @NotNull
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
    private UserAudit userAudit;

}
