package com.bookstoreapplication.bookstore.purchase.core;

import com.bookstoreapplication.bookstore.purchase.value_object.*;
import lombok.*;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Set;

@Table(name="purchases")
@Entity
@Getter
@AllArgsConstructor(access = AccessLevel.PROTECTED)
@NoArgsConstructor
public class Purchase implements Serializable {

    @EmbeddedId
    private PurchaseId purchaseId;
    private SimpleCustomerId userId;
    @Embedded
    private PurchaseDate purchaseDate;
    @Enumerated(EnumType.STRING)
    @NotNull(message = "Purchase status cannot be null")
    private PurchaseStatus purchaseStatus;

    Purchase(Customer customer, List<Purchase> customerInitializedPurchases) {
        customer.checkIfHasAnyInitializedPurchase(customerInitializedPurchases);
        this.userId = customer.getCustomerId();
        this.purchaseDate = new PurchaseDate(LocalDateTime.now());
        this.purchaseStatus = PurchaseStatus.INITIALIZED;
    }

    TotalPrice getTotalPrice(Set<PurchaseDetail> details){
        return null;
        //TODO
    }

}
