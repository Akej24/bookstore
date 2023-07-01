package com.bookstoreapplication.bookstore.purchase.cart;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

class InMemoryCartRepository implements CartRepository{

    private final Map<Long, Cart> cartDatabase = new HashMap<>();

    @Override
    public void save(Cart cart) {
        cartDatabase.put(cart.getCustomerId(), cart);
    }

    @Override
    public Optional<Cart> findByCustomerId(long customerId) {
        return Optional.ofNullable(cartDatabase.get(customerId));
    }

    @Override
    public boolean existsByCustomerId(long customerId) {
        return Optional.ofNullable(cartDatabase.get(customerId)).isPresent();
    }

    @Override
    public void deleteCartByCustomerId(long customerId) {
        cartDatabase.remove(customerId);
    }

    public int countAllRecords(){
        return cartDatabase.size();
    }
}
