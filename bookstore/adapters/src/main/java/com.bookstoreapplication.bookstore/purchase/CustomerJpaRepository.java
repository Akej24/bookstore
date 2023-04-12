package com.bookstoreapplication.bookstore.purchase;

import com.bookstoreapplication.bookstore.purchase.core.Customer;
import com.bookstoreapplication.bookstore.purchase.value_object.SimpleCustomerId;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CustomerJpaRepository extends JpaRepository<Customer, Long> {
}
