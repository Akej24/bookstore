package com.bookstoreapplication.bookstore.adapters.purchase;

import com.bookstoreapplication.bookstore.domain.purchase.core.PurchaseService;
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
