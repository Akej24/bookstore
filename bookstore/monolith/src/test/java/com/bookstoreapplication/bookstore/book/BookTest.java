package com.bookstoreapplication.bookstore.book;

import com.bookstoreapplication.bookstore.book.value_object.*;
import com.bookstoreapplication.bookstore.purchase.exception.NotEnoughBooksInMagazineException;
import com.bookstoreapplication.bookstore.purchase.value_object.BooksAmount;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.Month;

import static org.junit.jupiter.api.Assertions.*;

class BookTest {

    @Test
    @DisplayName("Should return true when update data with false availability matches updated book")
    public void createBook_and_updateBookWithFalseStatus_and_checkMatch() {
        Book book = new Book(getBookInitialState());
        BookCommand updateData = getBookUpdateDataWithStatusFalse();
        Book updatedBook = book.update(updateData);

        assertEquals(updateData.bookTitle().getBookTitle(), updatedBook.getBookTitle().getBookTitle());
        assertEquals(updateData.bookAuthor().getBookAuthor(), updatedBook.getBookAuthor().getBookAuthor());
        assertEquals(updateData.releaseDate().getReleaseDate().toString(), updatedBook.getReleaseDate().getReleaseDate().toString());
        assertEquals(updateData.numberOfPages().getNumberOfPages(), updatedBook.getNumberOfPages().getNumberOfPages());
        assertEquals(updateData.availabilityStatus().getAvailabilityStatus(), updatedBook.getAvailabilityStatus().getAvailabilityStatus());
        assertEquals(0, updatedBook.getAvailablePieces().getAvailablePieces());
        assertEquals(updateData.bookPrice().getBookPrice().doubleValue(), updatedBook.getBookPrice().getBookPrice().doubleValue());
    }

    @Test
    @DisplayName("Should return true when update data with true availability matches updated book")
    public void createBook_and_updateBookWithTrueStatus_and_checkMatch() {
        Book book = new Book(getBookInitialState());
        BookCommand updateData = getBookUpdateDataWithStatusTrue();
        Book updatedBook = book.update(updateData);

        assertEquals(updateData.bookTitle().getBookTitle(), updatedBook.getBookTitle().getBookTitle());
        assertEquals(updateData.bookAuthor().getBookAuthor(), updatedBook.getBookAuthor().getBookAuthor());
        assertEquals(updateData.releaseDate().getReleaseDate().toString(), updatedBook.getReleaseDate().getReleaseDate().toString());
        assertEquals(updateData.numberOfPages().getNumberOfPages(), updatedBook.getNumberOfPages().getNumberOfPages());
        assertEquals(updateData.availabilityStatus().getAvailabilityStatus(), updatedBook.getAvailabilityStatus().getAvailabilityStatus());
        assertEquals(updateData.availablePieces().getAvailablePieces(), updatedBook.getAvailablePieces().getAvailablePieces());
        assertEquals(updateData.bookPrice().getBookPrice().doubleValue(), updatedBook.getBookPrice().getBookPrice().doubleValue());
    }

    @Test
    @DisplayName("Should return true when book available pieces after decrement match the expected value")
    void decreaseAvailablePieces_and_checkMatch() {
        Book book = new Book(getBookInitialState());
        book.decreaseAvailablePieces(new BooksAmount(6));
        assertEquals(book.getAvailablePieces().getAvailablePieces(), 4);
    }
    @Test
    @DisplayName("Should throw NotEnoughBooksInMagazineException when the number of books to decrement is greater than the available books")
    void decreaseAvailablePieces_and_throwNotEnoughBooksInMagazineException() {
        Book book = new Book(getBookInitialState());
        Exception exception = assertThrows(NotEnoughBooksInMagazineException.class, () -> book.decreaseAvailablePieces(new BooksAmount(12)));
        assertTrue(exception.getMessage().contains("Not enough"));
    }

    private static BookCommand getBookInitialState() {
        return new BookCommand(
                new BookTitle("Example Title"),
                new BookAuthor("Example Author"),
                new ReleaseDate(LocalDate.of(2012, Month.MARCH, 23)),
                new NumberOfPages(200),
                new AvailabilityStatus(true),
                new AvailablePieces(10),
                new BookPrice(BigDecimal.valueOf(19.99))
        );
    }

    private static BookCommand getBookUpdateDataWithStatusFalse() {
        return new BookCommand(
                new BookTitle("Updated Title"),
                new BookAuthor("Updated Author"),
                new ReleaseDate(LocalDate.of(2017, Month.MAY, 12)),
                new NumberOfPages(176),
                new AvailabilityStatus(false),
                new AvailablePieces(34),
                new BookPrice(BigDecimal.valueOf(39.75))
        );
    }
    private static BookCommand getBookUpdateDataWithStatusTrue() {
        return new BookCommand(
                new BookTitle("Another Updated Title"),
                new BookAuthor("Another Updated Author"),
                new ReleaseDate(LocalDate.of(2019, Month.FEBRUARY, 9)),
                new NumberOfPages(1764),
                new AvailabilityStatus(true),
                new AvailablePieces(125),
                new BookPrice(BigDecimal.valueOf(127.87))
        );
    }
}