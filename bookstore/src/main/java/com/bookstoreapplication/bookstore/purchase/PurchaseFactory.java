package com.bookstoreapplication.bookstore.purchase;

import com.bookstoreapplication.bookstore.purchase.vo.Customer;
import com.bookstoreapplication.bookstore.purchase.vo.PurchaseDate;
import com.bookstoreapplication.bookstore.purchase.vo.PurchaseStatus;

import java.time.LocalDateTime;

public class PurchaseFactory {

    public static Purchase createNewPurchase(Customer customer){
        Purchase purchase = new Purchase();
        //purchase.setCustomer(customer);
        purchase.setPurchaseDate(new PurchaseDate(LocalDateTime.now()));
        //purchase.setTotalPrice();
        purchase.setPurchaseStatus(PurchaseStatus.INITIALIZED);
        return purchase;
    }

}
