package com.bookstoreapplication.bookstore.adapters.repositories;

import com.bookstoreapplication.bookstore.domain.purchase.core.Purchase;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface PurchaseJpaRepository extends JpaRepository<Purchase, Long> {
    List<Purchase> findByCustomer_CustomerIdAndPurchaseStatusInitialized(long userId);

    List<Purchase> findByUserUserId(long userId);
}
