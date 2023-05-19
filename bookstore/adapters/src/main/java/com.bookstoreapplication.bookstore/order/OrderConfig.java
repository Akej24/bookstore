package com.bookstoreapplication.bookstore.order;

import lombok.AllArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
@AllArgsConstructor
public class OrderConfig {

    private final CartRedisRepository cartRedisRepository;
    private final CheckoutCartRedisRepository checkoutCartRedisRepository;
    private final BookProductJpaRepository bookProductJpaRepository;
    private final OrderDetailsJpaRepository orderDetailsJpaRepository;
    private final OrderJpaRepository orderJpaRepository;
    private final PlacedOrderPublisher placedOrderPublisher;

    @Bean
    CartHandler cartHandler(){
        return new CartHandler(
                cartRedisRepository,
                checkoutCartRedisRepository,
                bookProductJpaRepository);
    }

    @Bean
    CheckoutCartHandler checkoutCartHandler(){
        return new CheckoutCartHandler(
                cartHandler(),
                cartRedisRepository,
                checkoutCartRedisRepository,
                orderDetailsJpaRepository,
                orderJpaRepository,
                placedOrderPublisher);
    }

    @Bean
    OrderHandler orderHandler(){
        return new OrderHandler(
                orderDetailsJpaRepository,
                orderJpaRepository
        );
    }
}
