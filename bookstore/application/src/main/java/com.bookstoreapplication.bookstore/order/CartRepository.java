package com.bookstoreapplication.bookstore.order;

import java.util.Optional;

interface CartRepository {

    void save(Cart cart);

    Optional<Cart> findByCustomerId(long customerId);

    boolean existsByCustomerId(long customerId);


    void deleteAllByCustomerId(long customerId);
}
