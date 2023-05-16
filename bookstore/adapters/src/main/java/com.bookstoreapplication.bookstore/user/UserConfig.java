package com.bookstoreapplication.bookstore.user;

import lombok.AllArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
@AllArgsConstructor
class UserConfig {

    private final UserJpaRepository userJpaRepository;
    private final UserBCryptPasswordEncoder userBCryptPasswordEncoder;

    @Bean
    public UserHandler userHandler(){
        return new UserHandler(userJpaRepository, userBCryptPasswordEncoder);
    }
}
