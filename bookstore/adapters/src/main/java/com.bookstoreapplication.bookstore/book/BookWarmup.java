package com.bookstoreapplication.bookstore.book;

import com.bookstoreapplication.bookstore.book.value_object.*;
import lombok.AllArgsConstructor;
import org.springframework.context.event.ContextRefreshedEvent;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.Month;

@Component
@AllArgsConstructor
class BookWarmup {

    private final BookHandler bookHandler;

    @EventListener
    public void insertStartupData(ContextRefreshedEvent contextRefreshedEvent){
        bookHandler.addBookToDatabase(new BookCommand(
                new BookTitle("Metro 2033"),
                new BookAuthor("Dmitry Glukhovsky"),
                new ReleaseDate(LocalDate.of(2002, Month.MARCH, 3)),
                new NumberOfPages(400),
                new AvailabilityStatus(true),
                new AvailablePieces(8),
                new BookPrice(BigDecimal.valueOf(35.85))
        ));
        bookHandler.addBookToDatabase(new BookCommand(
                new BookTitle("The Magicians' Guild"),
                new BookAuthor("Trudi Canavan"),
                new ReleaseDate(LocalDate.of(2001, Month.NOVEMBER, 21)),
                new NumberOfPages(320),
                new AvailabilityStatus(true),
                new AvailablePieces(12),
                new BookPrice(BigDecimal.valueOf(28.37))
        ));
        bookHandler.addBookToDatabase(new BookCommand(
                new BookTitle("The Witcher: Blood of Elves"),
                new BookAuthor("Andrzej Sapkowski"),
                new ReleaseDate(LocalDate.of(1994, Month.JULY, 16)),
                new NumberOfPages(283),
                new AvailabilityStatus(false),
                new AvailablePieces(4),
                new BookPrice(BigDecimal.valueOf(39.92))
        ));
    }
}
