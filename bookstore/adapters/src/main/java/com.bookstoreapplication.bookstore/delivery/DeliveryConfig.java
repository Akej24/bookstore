package com.bookstoreapplication.bookstore.delivery;

import lombok.AllArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
@AllArgsConstructor
class DeliveryConfig {

    private final DeliveryJpaRepository deliveryJpaRepository;

    @Bean
    DeliveryHandler deliveryHandler(){
        return new DeliveryHandler(
                deliveryJpaRepository
        );
    }

}
