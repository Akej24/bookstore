package com.bookstoreapplication.bookstore.purchase.cart;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
interface BookProductJpaRepository extends BookProductRepository, JpaRepository<BookProduct, Long> {

}
