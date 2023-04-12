package com.bookstoreapplication.bookstore.purchase;

import com.bookstoreapplication.bookstore.purchase.core.PurchaseDetail;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PurchaseDetailJpaRepository extends JpaRepository<PurchaseDetail, Long> {

    void deleteByPurchaseId_PurchaseId(long purchaseId);

}
