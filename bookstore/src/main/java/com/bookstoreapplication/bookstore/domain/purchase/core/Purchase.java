package com.bookstoreapplication.bookstore.domain.purchase;

import com.bookstoreapplication.bookstore.domain.purchase.value_objects.PurchaseDate;
import com.bookstoreapplication.bookstore.domain.purchase.value_objects.PurchaseId;
import com.bookstoreapplication.bookstore.domain.purchase.value_objects.PurchaseStatus;
import com.bookstoreapplication.bookstore.domain.purchase.value_objects.TotalPrice;
import com.bookstoreapplication.bookstore.purchase.value_objects.*;
import lombok.*;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Set;

@Entity
@Table(name = "purchases")
@Getter
@Setter
@NoArgsConstructor
public class Purchase implements Serializable {

    @Id
    private PurchaseId purchaseId;
    private Customer customer;
    private PurchaseDate purchaseDate;
    @Enumerated(EnumType.STRING)
    @NotNull(message = "Purchase status cannot be null")
    private PurchaseStatus purchaseStatus;

    Purchase(Customer customer, List<Purchase> customerInitializedPurchases) {
        customer.checkIfHasAnyInitializedPurchase(customerInitializedPurchases);
        this.customer = customer;
        this.purchaseDate = new PurchaseDate(LocalDateTime.now());
        this.purchaseStatus = PurchaseStatus.INITIALIZED;
    }

    TotalPrice getTotalPrice(Set<PurchaseDetail> details){
        //TODO
    }

}
