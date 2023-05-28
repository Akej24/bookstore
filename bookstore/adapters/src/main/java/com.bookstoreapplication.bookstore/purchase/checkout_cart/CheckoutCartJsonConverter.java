package com.bookstoreapplication.bookstore.purchase.checkout_cart;

import com.bookstoreapplication.bookstore.delivery.AddressJsonConverter;
import com.bookstoreapplication.bookstore.purchase.value_object.*;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ObjectNode;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@AllArgsConstructor
public class CheckoutCartJsonConverter {

    private final ObjectMapper objectMapper;

    public String toJson(CheckoutCart checkoutCart) {
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

    public CheckoutCart fromJson(String json) {
        try {
            JsonNode rootNode = objectMapper.readTree(json);
            long customerId = rootNode.get("cartId").asLong();
            Address address = null;
            String paymentMethod = null;

            if(rootNode.has("address")) {
                JsonNode addressNode = rootNode.get("address");
                address = AddressJsonConverter.extractAddress(addressNode);
            }
            if(rootNode.has("paymentMethod")){
                paymentMethod = rootNode.get("paymentMethod").asText();
            }
            if(paymentMethod == null){
                return new CheckoutCart(customerId,address,null);
            }
            return new CheckoutCart(customerId,address,PaymentMethod.valueOf(paymentMethod));
        } catch (JsonProcessingException e) {
            throw new RuntimeException("Unable to deserialize checkout cart");
        }
    }
}
