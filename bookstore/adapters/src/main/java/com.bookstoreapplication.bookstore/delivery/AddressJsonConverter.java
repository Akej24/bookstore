package com.bookstoreapplication.bookstore.delivery;

import com.bookstoreapplication.bookstore.purchase.checkout_cart.Address;
import com.bookstoreapplication.bookstore.purchase.value_object.*;
import com.bookstoreapplication.bookstore.user.value_objects.FirstName;
import com.bookstoreapplication.bookstore.user.value_objects.LastName;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ObjectNode;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.UUID;

@Component
@AllArgsConstructor
public class AddressJsonConverter {

    private final ObjectMapper objectMapper;

    public String toJson(UUID orderId, Address address) {
        try {
            ObjectNode rootNode = objectMapper.createObjectNode();
            rootNode.put("orderId", orderId.toString());

            ObjectNode addressNode = objectMapper.createObjectNode();
            addressNode.put("firstName", address.getFirstName().getFirstName());
            addressNode.put("lastName", address.getLastName().getLastName());
            addressNode.put("phoneNumber", address.getPhoneNumber().getPhoneNumber());
            addressNode.put("street", address.getStreet().getStreet());
            addressNode.put("streetNumber", address.getStreetNumber().getStreetNumber());
            addressNode.put("zipCode", address.getZipCode().getZipCode());
            addressNode.put("city", address.getCity().getCity());
            rootNode.set("address", addressNode);

            return objectMapper.writeValueAsString(rootNode);
        } catch (JsonProcessingException e) {
            throw new RuntimeException("Unable to serialize delivery message");
        }
    }

    DeliveryCommand fromJson(String jsonDelivery) {
        try {
            JsonNode rootNode = objectMapper.readTree(jsonDelivery);
            String orderId = rootNode.get("orderId").asText();
            Address address = extractAddress(rootNode);

            return new DeliveryCommand(UUID.fromString(orderId), address);
        } catch (JsonProcessingException e) {
            throw new RuntimeException("Unable to deserialize delivery message");
        }
    }

    public static Address extractAddress(JsonNode rootNode) {
        JsonNode addressNode = rootNode.get("address");
        String firstName = addressNode.get("firstName").asText();
        String lastName = addressNode.get("lastName").asText();
        String phoneNumber = addressNode.get("phoneNumber").asText();
        String street = addressNode.get("street").asText();
        Integer streetNumber = addressNode.get("streetNumber").asInt();
        String zipCode = addressNode.get("zipCode").asText();
        String city = addressNode.get("city").asText();

        return new Address(
                new FirstName(firstName),
                new LastName(lastName),
                new PhoneNumber(phoneNumber),
                new Street(street),
                new StreetNumber(streetNumber),
                new ZipCode(zipCode),
                new City(city));
    }
}
