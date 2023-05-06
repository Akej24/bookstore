package com.bookstoreapplication.bookstore.order;

import java.util.Optional;

interface CustomerRepository {

    Optional<Customer> findById(Long customerId);

}
