package com.bookstoreapplication.bookstore.purchase.checkout_cart;

import lombok.AllArgsConstructor;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
@AllArgsConstructor
class CheckoutCartRedisRepository implements CheckoutCartRepository {

    private final RedisTemplate<String, String> redisTemplate;
    private final CheckoutCartJsonConverter checkoutCartJsonConverter;

    private String getRedisKey(long customerId) {
        return "checkoutCart:" + customerId;
    }

    @Override
    public void save(CheckoutCart checkoutCart) {
        String key = getRedisKey(checkoutCart.getCartId());
        redisTemplate.opsForValue().set(key, checkoutCartJsonConverter.toJson(checkoutCart));
    }

    @Override
    public Optional<CheckoutCart> findByCart_CustomerId(long customerId) {
        String key = getRedisKey(customerId);
        Object value = redisTemplate.opsForValue().get(key);
        if (value != null) {
            return Optional.of(checkoutCartJsonConverter.fromJson(value.toString()));
        } else {
            return Optional.empty();
        }
    }

    @Override
    public boolean existsByCart_CustomerId(long customerId) {
        String key = getRedisKey(customerId);
        Object value = redisTemplate.opsForValue().get(key);
        return value != null;
    }

    @Override
    public void deleteCheckoutCartByCustomerId(long customerId) {
        String key = getRedisKey(customerId);
        redisTemplate.delete(key);
    }
}
