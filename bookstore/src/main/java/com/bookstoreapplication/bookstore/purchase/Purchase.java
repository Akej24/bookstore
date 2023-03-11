package com.bookstoreapplication.bookstore.purchase;

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
public class Purchase {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long purchaseId;

    @OneToMany(mappedBy = "purchase")
    private Set<PurchaseDetail> purchaseDetails;

    @NotNull(message = "Purchase date must be not null")
    private LocalDateTime purchaseDate = LocalDateTime.now();

    @DecimalMin(value = "0.0", message = "The minimum value of total price is 0.0")
    @NotNull(message = "Total price must be not null")
    private Double totalPrice;

    @Enumerated(EnumType.STRING)
    @NotNull(message = "Purchase status cannot be null")
    private PurchaseStatus purchaseStatus = PurchaseStatus.INITIALIZED;

}
