package com.bookstoreapplication.bookstore.payment;

import lombok.AllArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
@AllArgsConstructor
class PaymentConfig {

    private final PaymentJpaRepository paymentJpaRepository;
    private final PaymentDeserializer paymentDeserializer;

    @Bean
    PaymentService paymentService(){
        return new PaymentService(
                paymentJpaRepository,
                paymentDeserializer
        );
    }

}
