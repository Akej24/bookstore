package com.bookstoreapplication.bookstore.purchase.order;

import com.bookstoreapplication.bookstore.book.BooksDecrementJsonConverter;
import com.bookstoreapplication.bookstore.delivery.AddressJsonConverter;
import com.bookstoreapplication.bookstore.payment.PaymentJsonConverter;
import com.bookstoreapplication.bookstore.purchase.cart.Cart;
import com.bookstoreapplication.bookstore.purchase.checkout_cart.Address;
import com.bookstoreapplication.bookstore.purchase.checkout_cart.CheckoutCart;
import lombok.RequiredArgsConstructor;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.UUID;

@Component
@RequiredArgsConstructor
class OrderRabbitMQTemplate implements OrderMQTemplate {

    @Value("${bookstore.rabbitmq.routing-keys.payment}")
    private String paymentRoutingKey;

    @Value("${bookstore.rabbitmq.routing-keys.books-decrement}")
    private String booksDecrementRoutingKey;

    @Value("${bookstore.rabbitmq.routing-keys.delivery}")
    private String deliveryRoutingKey;

    private final BooksDecrementJsonConverter booksDecrementJsonConverter;
    private final AddressJsonConverter addressJsonConverter;
    private final PaymentJsonConverter paymentJsonConverter;
    private final RabbitTemplate rabbitTemplate;

    @Override
    public void publishPayment(Cart cart, CheckoutCart customerCheckoutCart, UUID orderId) {
        rabbitTemplate.convertAndSend(paymentRoutingKey, paymentJsonConverter.toJson(cart, customerCheckoutCart, orderId));
    }

    @Override
    public void publishBooksDecrement(List<OrderDetail> orderDetails) {
        rabbitTemplate.convertAndSend(booksDecrementRoutingKey, booksDecrementJsonConverter.toJson(orderDetails));
    }

    @Override
    public void publishDelivery(UUID orderId, Address address) {
        rabbitTemplate.convertAndSend(deliveryRoutingKey, addressJsonConverter.toJson(orderId, address));
    }


}
