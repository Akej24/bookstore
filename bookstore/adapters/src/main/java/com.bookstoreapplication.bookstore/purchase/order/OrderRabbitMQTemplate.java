package com.bookstoreapplication.bookstore.purchase.order;

import com.bookstoreapplication.bookstore.book.BooksDecrementJsonConverter;
import com.bookstoreapplication.bookstore.payment.PaymentJsonConverter;
import com.bookstoreapplication.bookstore.purchase.cart.Cart;
import com.bookstoreapplication.bookstore.purchase.checkout_cart.Address;
import com.bookstoreapplication.bookstore.purchase.checkout_cart.CheckoutCart;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ObjectNode;
import lombok.AllArgsConstructor;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.UUID;

@Component
@AllArgsConstructor
class OrderRabbitMQTemplate implements OrderMQTemplate {

    private static final String ROUTING_KEY_PAYMENT = "payment";
    private static final String ROUTING_KEY_BOOKS_DECREMENT = "books_decrement";
    private static final String ROUTING_KEY_DELIVERY = "delivery";
    private final BooksDecrementJsonConverter booksDecrementJsonConverter;
    private final PaymentJsonConverter paymentJsonConverter;
    private final RabbitTemplate rabbitTemplate;
    private final ObjectMapper objectMapper;

    @Override
    public void publishPayment(Cart cart, CheckoutCart customerCheckoutCart, UUID orderId) {
        rabbitTemplate.convertAndSend(ROUTING_KEY_PAYMENT, paymentJsonConverter.toJson(cart, customerCheckoutCart, orderId));
    }

    @Override
    public void publishBooksDecrement(List<OrderDetail> orderDetails) {
        rabbitTemplate.convertAndSend(ROUTING_KEY_BOOKS_DECREMENT, booksDecrementJsonConverter.toJson(orderDetails));
    }

    @Override
    public void publishDelivery(UUID orderId, Address address) {
        rabbitTemplate.convertAndSend(ROUTING_KEY_DELIVERY, toDeliveryJson(orderId, address));
    }

    private String toDeliveryJson(UUID orderId, Address address) {
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

}
