package com.bookstoreapplication.bookstore.book;

import lombok.AllArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
@AllArgsConstructor
class BookConfig {

    private final BookJpaRepository bookJpaRepository;

    @Bean
    public BookHandler bookHandler(){
        return new BookHandler(bookJpaRepository);
    }
}
