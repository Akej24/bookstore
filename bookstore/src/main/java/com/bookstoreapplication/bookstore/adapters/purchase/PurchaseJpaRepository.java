package com.bookstoreapplication.bookstore.adapters.purchase;

import com.bookstoreapplication.bookstore.domain.purchase.core.Purchase;
import com.bookstoreapplication.bookstore.domain.purchase.value_object.PurchaseStatus;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface PurchaseJpaRepository extends JpaRepository<Purchase, Long> {
    List<Purchase> findByUserId_UserIdAndPurchaseStatus(long userId, PurchaseStatus purchaseStatus);

    List<Purchase> findByUserId_UserId(long userId);
}