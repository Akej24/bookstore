package com.bookstoreapplication.bookstore.purchase.order;

import com.bookstoreapplication.bookstore.purchase.cart.CartHandler;
import com.bookstoreapplication.bookstore.purchase.checkout_cart.CheckoutCartHandler;
import lombok.AllArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
@AllArgsConstructor
class OrderConfig {

    private final CartHandler cartHandler;
    private final CheckoutCartHandler checkoutCartHandler;
    private final OrderDetailsJpaRepository orderDetailsJpaRepository;
    private final OrderJpaRepository orderJpaRepository;
    private final OrderRabbitMQTemplate orderRabbitMQTemplate;
    private final PlacedOrderPublisher placedOrderPublisher;

    @Bean
    OrderHandler orderHandler(){
        return new OrderHandler(
                cartHandler,
                checkoutCartHandler,
                orderDetailsJpaRepository,
                orderJpaRepository,
                orderRabbitMQTemplate,
                placedOrderPublisher
        );
    }
}
