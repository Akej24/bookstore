package com.bookstoreapplication.bookstore.purchase;

import com.bookstoreapplication.bookstore.purchase.core.BookProduct;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface BookProductRepository extends JpaRepository<BookProduct, Long> {


}
