package com.bookstoreapplication.bookstore.order;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.AllArgsConstructor;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
@AllArgsConstructor
class CartRedisRepository implements CartRepository {

    private final RedisTemplate<String, Object> redisTemplate;
    private final ObjectMapper objectMapper;

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
    public void deleteAllByCustomerId(long customerId) {
        String key = getRedisKey(customerId);
        redisTemplate.delete(key);
    }

    private byte[] serialize(Object object) {
        try {
            return objectMapper.writeValueAsBytes(object);
        } catch (JsonProcessingException e) {
            throw new RuntimeException("Unable to serialize object", e);
        }
    }

    private Cart deserialize(String json) {
        try {
            return objectMapper.readValue(json, Cart.class);
        } catch (JsonProcessingException e) {
            throw new RuntimeException("Unable to deserialize object", e);
        }
    }
}
