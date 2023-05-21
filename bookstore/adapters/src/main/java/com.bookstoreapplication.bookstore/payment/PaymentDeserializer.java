package com.bookstoreapplication.bookstore.payment;

import com.bookstoreapplication.bookstore.order.value_object.PaymentMethod;
import com.bookstoreapplication.bookstore.order.value_object.TotalPrice;
import com.bookstoreapplication.bookstore.payment.value_object.ServiceType;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;
import java.util.UUID;

@Component
@AllArgsConstructor
class PaymentDeserializer implements IPaymentDeserializer {

    private final ObjectMapper objectMapper;

    @Override
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
