package com.bookstoreapplication.bookstore.purchase.cart;

import java.util.Optional;

interface CartRepository {

    void save(Cart cart);

    Optional<Cart> findByCustomerId(long customerId);

    boolean existsByCustomerId(long customerId);

    void deleteCartByCustomerId(long customerId);

}
