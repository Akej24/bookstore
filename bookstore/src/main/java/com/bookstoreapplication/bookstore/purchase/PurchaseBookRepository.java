package com.bookstoreapplication.bookstore.purchase;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PurchaseBookRepository extends JpaRepository<PurchaseDetails, Long> {

}
