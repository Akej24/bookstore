package com.bookstoreapplication.bookstore.order;

interface CheckoutCartRepository {

    void save(CheckoutCart checkout);

    CheckoutCart findByCart_CustomerId(long customerId);

}
