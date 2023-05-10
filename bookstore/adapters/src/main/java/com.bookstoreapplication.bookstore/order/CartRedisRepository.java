package com.bookstoreapplication.bookstore.order;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ArrayNode;
import com.fasterxml.jackson.databind.node.ObjectNode;
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

    private String serialize(Object object) {
        try {
            if (object instanceof Cart cart) {

                ObjectNode rootNode = objectMapper.createObjectNode();
                rootNode.put("customerId", cart.getCustomerId());

                ArrayNode cartLinesNode = objectMapper.createArrayNode();
                for (CartLine cartLine : cart.getCartLines()) {
                    ObjectNode cartLineNode = objectMapper.createObjectNode();

                    ObjectNode bookProductNode = objectMapper.createObjectNode();
                    bookProductNode.put("bookId", cartLine.getBookProduct().getBookId());
                    bookProductNode.put("bookTitle", cartLine.getBookProduct().getBookTitle().getBookTitle());
                    bookProductNode.put("bookAuthor", cartLine.getBookProduct().getBookAuthor().getBookAuthor());
                    bookProductNode.put("availabilityStatus", cartLine.getBookProduct().getAvailabilityStatus().getAvailabilityStatus());
                    bookProductNode.put("availablePieces", cartLine.getBookProduct().getAvailablePieces().getAvailablePieces());
                    bookProductNode.put("bookPrice", cartLine.getBookProduct().getBookPrice().getBookPrice());
                    cartLineNode.set("bookProduct", bookProductNode);

                    ObjectNode amountNode = objectMapper.createObjectNode();
                    amountNode.put("booksAmount", cartLine.getAmount().getBooksAmount());
                    cartLineNode.set("amount", amountNode);

                    cartLinesNode.add(cartLineNode);
                }
                rootNode.set("cartLines", cartLinesNode);

                rootNode.put("totalPrice", cart.getTotalPrice().getTotalPrice());

                return objectMapper.writeValueAsString(rootNode);
            } else {
                throw new RuntimeException("Object is not of type Cart");
            }
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
