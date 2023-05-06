package com.bookstoreapplication.bookstore.order;

interface CartRepository {

    void save(Cart cart);

    Cart findByCustomerId(long customerId);

}
