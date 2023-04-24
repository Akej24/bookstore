package com.bookstoreapplication.bookstore.purchase;

import com.bookstoreapplication.bookstore.purchase.value_object.PurchaseStatus;

import java.util.List;

interface PurchaseRepository {
    List<Purchase> findByUserId_UserIdAndPurchaseStatus(long userId, PurchaseStatus purchaseStatus);

    List<Purchase> findByUserId_UserId(long userId);

    Purchase save(Purchase createdPurchase);
}
