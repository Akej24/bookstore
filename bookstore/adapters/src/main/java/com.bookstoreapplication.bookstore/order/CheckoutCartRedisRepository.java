package com.bookstoreapplication.bookstore.order;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.AllArgsConstructor;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
@AllArgsConstructor
class CheckoutCartRedisRepository implements CheckoutCartRepository {

    private final RedisTemplate<String, Object> redisTemplate;
    private final ObjectMapper objectMapper;

    private String getRedisKey(long customerId) {
        return "checkoutCart:" + customerId;
    }

    @Override
    public void save(CheckoutCart checkoutCart) {
        String key = getRedisKey(checkoutCart.getCart().getCustomerId());
        redisTemplate.opsForValue().set(key, serialize(checkoutCart));
    }

    @Override
    public Optional<CheckoutCart> findByCart_CustomerId(long customerId) {
        String key = getRedisKey(customerId);
        Object value = redisTemplate.opsForValue().get(key);
        if (value != null) {
            return Optional.of(deserialize(value.toString()));
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

    private CheckoutCart deserialize(String json) {
        try {
            return objectMapper.readValue(json, CheckoutCart.class);
        } catch (JsonProcessingException e) {
            throw new RuntimeException("Unable to deserialize object", e);
        }
    }

}
