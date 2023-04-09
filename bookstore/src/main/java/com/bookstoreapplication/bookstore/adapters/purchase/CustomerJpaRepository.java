package com.bookstoreapplication.bookstore.adapters.purchase;

import com.bookstoreapplication.bookstore.domain.purchase.core.Customer;
import com.bookstoreapplication.bookstore.domain.purchase.value_object.SimpleCustomerId;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CustomerJpaRepository extends JpaRepository<Customer, Long> {
}
