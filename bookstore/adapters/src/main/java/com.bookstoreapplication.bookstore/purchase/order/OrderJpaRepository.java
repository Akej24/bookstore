package com.bookstoreapplication.bookstore.purchase.order;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
interface OrderJpaRepository extends OrderRepository, JpaRepository<Order, Long> {

}
