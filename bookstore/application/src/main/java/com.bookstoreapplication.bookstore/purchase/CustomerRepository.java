package com.bookstoreapplication.bookstore.purchase;

import java.util.Optional;

interface CustomerRepository {

    Optional<Customer> findById(Long customerId);
}
