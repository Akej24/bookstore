package com.bookstoreapplication.bookstore.purchase;

interface CartRepository {

    void save(Cart cart);

    Cart findByCustomerId(long customerId);

}
