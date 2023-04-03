package com.bookstoreapplication.bookstore.purchase;

import com.bookstoreapplication.bookstore.purchase.vo.PurchaseStatus;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
interface PurchaseRepository extends JpaRepository<Purchase, Long> {

    List<Purchase> findByUserUserIdAndPurchaseStatus(long userId, PurchaseStatus status);

    List<Purchase> findByUserUserId(long userId);

}
