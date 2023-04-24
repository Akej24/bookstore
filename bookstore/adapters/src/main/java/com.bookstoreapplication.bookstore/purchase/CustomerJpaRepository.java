package com.bookstoreapplication.bookstore.purchase;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
interface CustomerJpaRepository extends CustomerRepository, JpaRepository<Customer, Long> {

}
