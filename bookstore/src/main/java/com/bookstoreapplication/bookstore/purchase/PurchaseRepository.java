package com.bookstoreapplication.bookstore.purchase;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface PurchaseRepository extends JpaRepository<Purchase, Long> {

    List<Purchase> findByUserUserIdAndPurchaseStatus(long userId, PurchaseStatus status);

    List<Purchase> findByUserUserId(long userId);

//    @Query("SELECT DISTINCT p FROM Purchase p JOIN FETCH p.purchaseDetails")
//    List<Purchase> findAllPurchasesWithDetailsByUserId(long userId);
}
