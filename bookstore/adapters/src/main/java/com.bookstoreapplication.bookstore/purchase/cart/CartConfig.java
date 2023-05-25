package com.bookstoreapplication.bookstore.purchase.cart;

import lombok.AllArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
@AllArgsConstructor
class CartConfig {

    private final CartRedisRepository cartRedisRepository;
    private final BookProductJpaRepository bookProductJpaRepository;

    @Bean
    CartHandler cartHandler(){
        return new CartHandler(
                cartRedisRepository,
                bookProductJpaRepository);
    }
}
