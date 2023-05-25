package com.bookstoreapplication.bookstore.purchase.cart;

import com.bookstoreapplication.bookstore.book.value_object.*;
import com.bookstoreapplication.bookstore.purchase.value_object.BooksAmount;
import com.bookstoreapplication.bookstore.purchase.value_object.TotalPrice;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ArrayNode;
import com.fasterxml.jackson.databind.node.ObjectNode;
import lombok.AllArgsConstructor;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Repository;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Repository
@AllArgsConstructor
class CartRedisRepository implements CartRepository {

    private final RedisTemplate<String, String> redisTemplate;
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
    public void deleteCartByCustomerId(long customerId) {
        String key = getRedisKey(customerId);
        redisTemplate.delete(key);
    }

    private String serialize(Cart cart) {
        try {
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
                cartLineNode.put("amount", cartLine.getAmount().getBooksAmount());
                cartLinesNode.add(cartLineNode);
            }
            rootNode.set("cartLines", cartLinesNode);

            rootNode.put("totalPrice", cart.getTotalPrice().getTotalPrice());
            return objectMapper.writeValueAsString(rootNode);
        } catch (JsonProcessingException e) {
            throw new RuntimeException("Unable to serialize cart");
        }
    }

    private Cart deserialize(String json) {
        try {
            JsonNode rootNode = objectMapper.readTree(json);
            long customerId = rootNode.get("customerId").asLong();

            List<CartLine> cartLines = new ArrayList<>();
            ArrayNode cartLinesNode = (ArrayNode) rootNode.get("cartLines");
            for (JsonNode cartLineNode : cartLinesNode) {
                JsonNode bookProductNode = cartLineNode.get("bookProduct");
                long bookId = bookProductNode.get("bookId").asLong();
                String bookTitle = bookProductNode.get("bookTitle").asText();
                String bookAuthor = bookProductNode.get("bookAuthor").asText();
                Boolean availabilityStatus = bookProductNode.get("availabilityStatus").asBoolean();
                Integer availablePieces = bookProductNode.get("availablePieces").asInt();
                BigDecimal bookPrice = bookProductNode.get("bookPrice").decimalValue();

                BookProduct bookProduct = new BookProduct(
                        bookId,
                        new BookTitle(bookTitle),
                        new BookAuthor(bookAuthor),
                        new AvailabilityStatus(availabilityStatus),
                        new AvailablePieces(availablePieces),
                        new BookPrice(bookPrice));

                Integer amount = cartLineNode.get("amount").asInt();
                cartLines.add(new CartLine(customerId, bookProduct, new BooksAmount(amount)));
            }
            BigDecimal totalPrice = rootNode.get("totalPrice").decimalValue();
            return new Cart(customerId, cartLines, new TotalPrice(totalPrice));
        } catch (JsonProcessingException e) {
            throw new RuntimeException("Unable to deserialize cart");
        }
    }

}
