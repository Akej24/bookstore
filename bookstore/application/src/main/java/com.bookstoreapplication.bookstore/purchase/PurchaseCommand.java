package com.bookstoreapplication.bookstore.purchase;

import com.bookstoreapplication.bookstore.purchase.value_object.SimpleCustomerId;
import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.Set;

@Getter
@AllArgsConstructor
public class PurchaseCommand {

    private SimpleCustomerId customerId;
    private Set<PurchaseCommandDetail> purchaseCommandDetails;

}
