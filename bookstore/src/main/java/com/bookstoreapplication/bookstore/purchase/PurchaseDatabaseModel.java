package com.bookstoreapplication.bookstore.purchase;

import com.bookstoreapplication.bookstore.user.account.User;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.*;

import java.time.LocalDateTime;
import java.util.Set;

@Entity
@Table(name = "purchases")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder(toBuilder = true)
public class PurchaseDatabaseModel {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long purchaseId;

    @ManyToOne
    @JoinColumn(name = "user_id", nullable = false)
    private User user;

    @OneToMany(mappedBy = "purchaseDatabaseModel")
    private Set<PurchaseDetails> purchaseDetails;

    @NotNull
    private LocalDateTime purchaseDate;
    @NotNull
    private double totalPrice;
    @Enumerated(EnumType.STRING)
    @NotNull
    private PurchaseStatus purchaseStatus;

}
