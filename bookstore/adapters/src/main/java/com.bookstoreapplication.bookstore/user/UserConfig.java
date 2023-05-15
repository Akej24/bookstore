package com.bookstoreapplication.bookstore.user;

import lombok.AllArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

@Configuration
@AllArgsConstructor
class UserConfig {

    private final UserJpaRepository userJpaRepository;
    private final BCryptPasswordEncoder bCryptPasswordEncoder;

    @Bean
    public UserHandler userHandler(){
        return new UserHandler(userJpaRepository, bCryptPasswordEncoder);
    }
}
