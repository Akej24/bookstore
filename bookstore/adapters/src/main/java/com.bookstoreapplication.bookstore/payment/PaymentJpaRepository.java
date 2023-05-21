package com.bookstoreapplication.bookstore.payment;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

interface PaymentJpaRepository extends PaymentRepository, JpaRepository<Payment, UUID> {
}
