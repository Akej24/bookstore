package com.bookstoreapplication.bookstore.purchase;

import com.bookstoreapplication.bookstore.user.account.query.SimpleUserQueryDto;
import lombok.*;

import javax.persistence.*;
import javax.validation.constraints.DecimalMin;
import javax.validation.constraints.NotNull;
import java.time.LocalDateTime;
import java.util.Set;

@Entity
@Table(name = "purchases")
@Getter
@NoArgsConstructor
@AllArgsConstructor
@Builder(toBuilder = true)
class Purchase {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long purchaseId;

    @ManyToOne
    @JoinColumn(name = "user_id")
    private SimpleUserQueryDto user;

    @OneToMany(mappedBy = "purchase")
    private Set<PurchaseDetail> purchaseDetails;

    @NotNull(message = "Purchase date must be not null")
    @Builder.Default
    private LocalDateTime purchaseDate = LocalDateTime.now();

    @DecimalMin(value = "0.0", message = "The minimum value of total price is 0.0")
    private Double totalPrice;

    @Enumerated(EnumType.STRING)
    @NotNull(message = "Purchase status cannot be null")
    @Builder.Default
    private PurchaseStatus purchaseStatus = PurchaseStatus.INITIALIZED;

}
