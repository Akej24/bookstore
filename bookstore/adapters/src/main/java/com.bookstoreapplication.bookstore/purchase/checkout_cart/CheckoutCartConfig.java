package com.bookstoreapplication.bookstore.purchase.checkout_cart;

import com.bookstoreapplication.bookstore.purchase.cart.CartHandler;
import lombok.AllArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
@AllArgsConstructor
class CheckoutCartConfig {

    private final CartHandler cartHandler;
    private final CheckoutCartRedisRepository checkoutCartRedisRepository;
    @Bean
    CheckoutCartHandler checkoutCartHandler(){
        return new CheckoutCartHandler(
                cartHandler,
                checkoutCartRedisRepository
        );
    }

}
