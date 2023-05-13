package com.bookstoreapplication.bookstore.order;

import com.bookstoreapplication.bookstore.order.value_object.*;
import com.bookstoreapplication.bookstore.user.value_objects.FirstName;
import com.bookstoreapplication.bookstore.user.value_objects.LastName;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ObjectNode;
import lombok.AllArgsConstructor;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
@AllArgsConstructor
class CheckoutCartRedisRepository implements CheckoutCartRepository {

    private final RedisTemplate<String, String> redisTemplate;
    private final ObjectMapper objectMapper;

    private String getRedisKey(long customerId) {
        return "checkoutCart:" + customerId;
    }

    @Override
    public void save(CheckoutCart checkoutCart) {
        String key = getRedisKey(checkoutCart.getCartId());
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

    private String serialize(CheckoutCart checkoutCart) {
        try {
            ObjectNode rootNode = objectMapper.createObjectNode();
            rootNode.put("cartId", checkoutCart.getCartId());
            if(checkoutCart.getAddress() != null) {
                ObjectNode addressNode = objectMapper.createObjectNode();
                addressNode.put("firstName", checkoutCart.getAddress().getFirstName().getFirstName());
                addressNode.put("lastName", checkoutCart.getAddress().getLastName().getLastName());
                addressNode.put("phoneNumber", checkoutCart.getAddress().getPhoneNumber().getPhoneNumber());
                addressNode.put("street", checkoutCart.getAddress().getStreet().getStreet());
                addressNode.put("streetNumber", checkoutCart.getAddress().getStreetNumber().getStreetNumber());
                addressNode.put("zipCode", checkoutCart.getAddress().getZipCode().getZipCode());
                addressNode.put("city", checkoutCart.getAddress().getCity().getCity());
                rootNode.set("address", addressNode);
            }
            if(checkoutCart.getPaymentMethod() != null) {
                rootNode.put("paymentMethod", checkoutCart.getPaymentMethod().toString());
            }
            return objectMapper.writeValueAsString(rootNode);
        } catch (JsonProcessingException e) {
            throw new RuntimeException("Unable to serialize checkout cart");
        }
    }

    private CheckoutCart deserialize(String json) {
        try {
            JsonNode rootNode = objectMapper.readTree(json);
            long customerId = rootNode.get("cartId").asLong();
            Address address = null;
            String paymentMethod = null;

            if(rootNode.has("address")) {
                JsonNode addressNode = rootNode.get("address");
                String firstName = addressNode.get("firstName").asText();
                String lastName = addressNode.get("lastName").asText();
                String phoneNumber = addressNode.get("phoneNumber").asText();
                String street = addressNode.get("street").asText();
                Integer streetNumber = addressNode.get("streetNumber").asInt();
                String zipCode = addressNode.get("zipCode").asText();
                String city = addressNode.get("city").asText();
                address = new Address(
                        new FirstName(firstName),
                        new LastName(lastName),
                        new PhoneNumber(phoneNumber),
                        new Street(street),
                        new StreetNumber(streetNumber),
                        new ZipCode(zipCode),
                        new City(city)
                );
            }
            if(rootNode.has("paymentMethod")){
                paymentMethod = rootNode.get("paymentMethod").asText();
            }
            if(paymentMethod == null){
                return new CheckoutCart(customerId,address,null);
            }
            return new CheckoutCart(customerId,address,PaymentMethod.valueOf(paymentMethod));
        } catch (JsonProcessingException e) {
            throw new RuntimeException("Unable to deserialize object", e);
        }
    }

}
