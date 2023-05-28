package com.bookstoreapplication.bookstore;


import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Component;

@Component
@AllArgsConstructor
public class Fdsf {

    @Value("${spring.mail.port}")
    private Integer p;

    @Bean
    public void f(){
        System.out.println(p);
    }

}
