package com.bookstoreapplication.bookstore.purchase;

import com.bookstoreapplication.bookstore.purchase.core.PurchaseService;
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
