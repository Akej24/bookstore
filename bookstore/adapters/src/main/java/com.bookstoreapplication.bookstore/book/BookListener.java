package com.bookstoreapplication.bookstore.book;

import lombok.AllArgsConstructor;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
@AllArgsConstructor
class BookListener {

    private final BooksDecrementJsonConverter booksDecrementJsonConverter;
    private final BookListenerHandler bookListenerHandler;

    @RabbitListener(queues = "books_decrement")
    public void decreaseBooksAmount(String json){
        List<BooksDecrementCommand> booksToDecrement = booksDecrementJsonConverter.fromJson(json);
        bookListenerHandler.decrementBooksAmount(booksToDecrement);
    }

}
