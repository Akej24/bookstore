package com.bookstoreapplication.bookstore.order;

import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
@RequiredArgsConstructor
class PurchaseConfig {

    @Bean
    PurchaseService purchaseService(){
        return new PurchaseService();
    }

}
