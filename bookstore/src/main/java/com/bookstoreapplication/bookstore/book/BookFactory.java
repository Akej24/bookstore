package com.bookstoreapplication.bookstore.book;

import com.bookstoreapplication.bookstore.book.vo.*;

public class BookFactory {

    public static Book createBook(BookRequest source) {

        Book book = new Book();
        setDetails(book, source);
        book.setBookAudit(new BookAudit());
        book.updateAvailability();
        return book;
    }

    public static Book updateBook(Book bookToUpdate, BookRequest source) {
        setDetails(bookToUpdate, source);
        return bookToUpdate;
    }

    private static void setDetails(Book bookToUpdate, BookRequest source) {
        bookToUpdate.setTitle(source.getTitle());
        bookToUpdate.setAuthor(source.getAuthor());
        bookToUpdate.setReleaseDate(source.getReleaseDate());
        bookToUpdate.setNumberOfPages(source.getNumberOfPages());
        bookToUpdate.setAvailabilityStatus(source.getStatus());
        bookToUpdate.setAvailablePieces(source.getAvailablePieces());
        bookToUpdate.setPrice(source.getPrice());
    }
}
