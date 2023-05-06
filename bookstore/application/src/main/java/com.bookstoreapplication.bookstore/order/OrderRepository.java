package com.bookstoreapplication.bookstore.order;

import com.bookstoreapplication.bookstore.order.value_object.PurchaseStatus;

import java.util.List;

interface OrderRepository {

    List<Order> findByUserId_UserIdAndPurchaseStatus(long userId, PurchaseStatus purchaseStatus);

    List<Order> findByUserId_UserId(long userId);

    Order save(Order createdOrder);

}
