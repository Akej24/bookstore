package com.bookstoreapplication.bookstore.payment;

import com.bookstoreapplication.bookstore.purchase.cart.Cart;
import com.bookstoreapplication.bookstore.purchase.checkout_cart.CheckoutCart;
import com.bookstoreapplication.bookstore.purchase.value_object.PaymentMethod;
import com.bookstoreapplication.bookstore.purchase.value_object.TotalPrice;
import com.bookstoreapplication.bookstore.payment.value_object.ServiceType;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ObjectNode;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;
import java.util.UUID;

@Component
@AllArgsConstructor
public class PaymentJsonConverter {

    private final ObjectMapper objectMapper;

    public String toJson(Cart cart, CheckoutCart customerCheckoutCart, UUID orderId) {
        try {
            ObjectNode rootNode = objectMapper.createObjectNode();
            rootNode.put("serviceType", ServiceType.ORDER.toString());
            rootNode.put("serviceId", orderId.toString());
            rootNode.put("paymentMethod", customerCheckoutCart.getPaymentMethod().toString());
            rootNode.put("totalPrice", cart.getTotalPrice().getTotalPrice());
            return objectMapper.writeValueAsString(rootNode);
        } catch (JsonProcessingException e) {
            throw new RuntimeException("Unable to serialize payment message");
        }
    }

    public Payment fromJson(String jsonPayment) {
        try {
            JsonNode rootNode = objectMapper.readTree(jsonPayment);
            String serviceType = rootNode.get("serviceType").asText();
            String serviceId = rootNode.get("serviceId").asText();
            String paymentMethod = rootNode.get("paymentMethod").asText();
            BigDecimal totalPrice = rootNode.get("totalPrice").decimalValue();

            return new Payment(
                    ServiceType.valueOf(serviceType),
                    UUID.fromString(serviceId),
                    PaymentMethod.valueOf(paymentMethod),
                    new TotalPrice(totalPrice)
            );
        } catch (JsonProcessingException e) {
            throw new RuntimeException("Unable to deserialize payment");
        }
    }
}
