package com.bookstoreapplication.bookstore.purchase.checkout_cart;

import java.util.Optional;

interface CheckoutCartRepository {

    void save(CheckoutCart checkout);

    Optional<CheckoutCart> findByCart_CustomerId(long customerId);

    boolean existsByCart_CustomerId(long customerId);

    void deleteCheckoutCartByCustomerId(long customerId);
}
