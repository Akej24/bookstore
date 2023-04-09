package com.bookstoreapplication.bookstore.adapters.repositories;

import com.bookstoreapplication.bookstore.domain.purchase.core.PurchaseDetail;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PurchaseDetailJpaRepository extends JpaRepository<PurchaseDetail, Long> {

    void deleteByPurchasePurchaseId(long purchaseId);

}
