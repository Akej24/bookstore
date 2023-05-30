package com.bookstoreapplication.bookstore.delivery;

import org.springframework.data.jpa.repository.JpaRepository;

interface DeliveryJpaRepository extends DeliveryRepository, JpaRepository<Delivery, Long> {


}
