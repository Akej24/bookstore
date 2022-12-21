package com.bookstoreapplication.bookstore.purchase;

import com.bookstoreapplication.bookstore.user.account.UserDatabaseModel;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;
import java.util.Set;

@Entity
@Table(name = "purchases")
@Getter
@Setter
public class Purchase {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long purchaseId;
    @ManyToOne
    @JoinColumn(name = "user_id", nullable = false)
    private UserDatabaseModel userDatabaseModelId;
    @OneToMany(mappedBy = "purchaseId")
    private Set<PurchaseBook> purchaseBooks;

    @NotNull
    private LocalDateTime purchaseDate;
    @NotNull
    private double totalPrice;
    @Enumerated(EnumType.STRING)
    @NotNull
    private PurchaseStatus purchaseStatus;
}
