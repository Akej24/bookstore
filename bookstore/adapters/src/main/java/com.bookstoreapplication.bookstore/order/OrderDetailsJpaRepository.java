package com.bookstoreapplication.bookstore.order;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
interface OrderDetailsJpaRepository extends OrderDetailsRepository, JpaRepository<OrderDetail,Long> {

}
