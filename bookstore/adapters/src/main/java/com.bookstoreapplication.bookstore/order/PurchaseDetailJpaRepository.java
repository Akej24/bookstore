package com.bookstoreapplication.bookstore.order;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
interface PurchaseDetailJpaRepository extends PurchaseDetailRepository, JpaRepository<PurchaseDetail, Long> {

}
