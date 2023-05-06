package com.bookstoreapplication.bookstore.purchase;

interface CheckoutCartRepository {

    void save(CheckoutCart checkout);

    CheckoutCart findByCart_CustomerId(long customerId);

}