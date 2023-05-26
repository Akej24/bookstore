package com.bookstoreapplication.bookstore.purchase.cart;

import lombok.AllArgsConstructor;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
@AllArgsConstructor
class CartRedisRepository implements CartRepository {

    private final RedisTemplate<String, String> redisTemplate;
    private final CartJsonConverter cartJsonConverter;

    private String getRedisKey(long customerId) {
        return "cart:" + customerId;
    }

    @Override
    public void save(Cart cart) {
        String key = getRedisKey(cart.getCustomerId());
        redisTemplate.opsForValue().set(key, serialize(cart));
    }

    @Override
    public Optional<Cart> findByCustomerId(long customerId) {
        String key = getRedisKey(customerId);
        Object value = redisTemplate.opsForValue().get(key);
        if (value != null) {
            return Optional.of(deserialize(value.toString()));
        } else {
            return Optional.empty();
        }
    }

    @Override
    public boolean existsByCustomerId(long customerId) {
        String key = getRedisKey(customerId);
        Object value = redisTemplate.opsForValue().get(key);
        return value != null;
    }

    @Override
    public void deleteCartByCustomerId(long customerId) {
        String key = getRedisKey(customerId);
        redisTemplate.delete(key);
    }

    private String serialize(Cart cart) {
        return cartJsonConverter.toJson(cart);
    }

    private Cart deserialize(String json) {
        return  cartJsonConverter.fromJson(json);
    }
}
