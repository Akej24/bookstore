package com.bookstoreapplication.bookstore.purchase;

import com.bookstoreapplication.bookstore.purchase.vo.*;
import lombok.*;

import javax.persistence.*;
import javax.validation.constraints.NotNull;

@Entity
@Table(name = "purchases")
@Getter
@Setter
@NoArgsConstructor
class Purchase {

    @Id
    private PurchaseId purchaseId;
    private Customer customer;
    private PurchaseDate purchaseDate;
    private TotalPrice totalPrice;
    @Enumerated(EnumType.STRING)
    @NotNull(message = "Purchase status cannot be null")
    private PurchaseStatus purchaseStatus;

}
