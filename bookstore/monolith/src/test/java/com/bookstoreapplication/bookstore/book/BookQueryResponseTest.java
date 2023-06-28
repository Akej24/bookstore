package com.bookstoreapplication.bookstore.book;

import com.bookstoreapplication.bookstore.book.value_object.*;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.Month;
import java.util.List;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.junit.jupiter.api.Assertions.assertEquals;

class BookQueryResponseTest {

    @Test
    @DisplayName("Should return true when first mapped book contains all correct field names and the second contains all correct values")
    void mapFromBooksListToBooksQueryResponseList() {
        var booksToMap = getBooksInitialStates();
        var mappedBooks = BookQueryResponse.from(booksToMap);
        assertThat(mappedBooks.get(0))
                .hasFieldOrProperty("bookId")
                .hasFieldOrProperty("bookTitle")
                .hasFieldOrProperty("bookAuthor")
                .hasFieldOrProperty("releaseDate")
                .hasFieldOrProperty("numberOfPages")
                .hasFieldOrProperty("availabilityStatus")
                .hasFieldOrProperty("availablePieces")
                .hasFieldOrProperty("bookPrice");

        var secondBook = booksToMap.get(1);
        var secondMappedBook = mappedBooks.get(1);
        assertEquals(secondBook.getBookId(), secondMappedBook.getBookId());
        assertEquals(secondBook.getBookTitle().getBookTitle(), secondMappedBook.getBookTitle().getBookTitle());
        assertEquals(secondBook.getBookAuthor().getBookAuthor(), secondMappedBook.getBookAuthor().getBookAuthor());
        assertEquals(secondBook.getReleaseDate().getReleaseDate().toString(), secondMappedBook.getReleaseDate().getReleaseDate().toString());
        assertEquals(secondBook.getNumberOfPages().getNumberOfPages(), secondMappedBook.getNumberOfPages().getNumberOfPages());
        assertEquals(secondBook.getAvailabilityStatus().getAvailabilityStatus(), secondMappedBook.getAvailabilityStatus().getAvailabilityStatus());
        assertEquals(secondBook.getAvailablePieces().getAvailablePieces(), secondMappedBook.getAvailablePieces().getAvailablePieces());
        assertEquals(secondBook.getBookPrice().getBookPrice().doubleValue(), secondMappedBook.getBookPrice().getBookPrice().doubleValue());
    }

    private static List<Book> getBooksInitialStates() {
        return List.of(getFirstBookInitialState(), getSecondBookInitialState());
    }

    private static Book getFirstBookInitialState() {
        return new Book(
                57,
                new BookTitle("Title1"),
                new BookAuthor("Author1"),
                new ReleaseDate(LocalDate.of(2010, Month.NOVEMBER, 11)),
                new NumberOfPages(56),
                new AvailabilityStatus(true),
                new AvailablePieces(17),
                new BookPrice(BigDecimal.valueOf(74.93)),
                new BookAudit()
        );
    }

    private static Book getSecondBookInitialState() {
        return new Book(
                42,
                new BookTitle("Title2"),
                new BookAuthor("Author2"),
                new ReleaseDate(LocalDate.of(2011, Month.DECEMBER, 22)),
                new NumberOfPages(546),
                new AvailabilityStatus(false),
                new AvailablePieces(11),
                new BookPrice(BigDecimal.valueOf(64.32)),
                new BookAudit()
        );
    }
}