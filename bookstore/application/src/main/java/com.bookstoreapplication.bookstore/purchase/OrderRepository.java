package com.bookstoreapplication.bookstore.purchase;

import com.bookstoreapplication.bookstore.purchase.value_object.PurchaseStatus;

import java.util.List;

interface OrderRepository {

    List<Order> findByUserId_UserIdAndPurchaseStatus(long userId, PurchaseStatus purchaseStatus);

    List<Order> findByUserId_UserId(long userId);

    Order save(Order createdOrder);

}
