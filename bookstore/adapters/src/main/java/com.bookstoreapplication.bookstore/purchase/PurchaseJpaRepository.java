package com.bookstoreapplication.bookstore.purchase;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
interface PurchaseJpaRepository extends PurchaseRepository, JpaRepository<Purchase, Long> {

}
