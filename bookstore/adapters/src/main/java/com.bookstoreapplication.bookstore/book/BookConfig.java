package com.bookstoreapplication.bookstore.book;

import lombok.AllArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
@AllArgsConstructor
class BookConfig {

    private final BookJpaRepository bookJpaRepository;

    @Bean
    public BookSharedHandler bookSharedHandler() {
        return new BookSharedHandler(bookJpaRepository);
    }

    @Bean
    public BookControllerHandler bookControllerHandler() {
        return new BookControllerHandler(bookSharedHandler(), bookJpaRepository);
    }

    @Bean
    public BookListenerHandler bookListenerHandler() {
        return new BookListenerHandler(bookSharedHandler(), bookJpaRepository);
    }
}
