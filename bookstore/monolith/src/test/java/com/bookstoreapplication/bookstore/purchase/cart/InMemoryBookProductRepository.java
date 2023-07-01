package com.bookstoreapplication.bookstore.purchase.cart;

import com.bookstoreapplication.bookstore.book.value_object.*;

import java.math.BigDecimal;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

class InMemoryBookProductRepository implements BookProductRepository{

    private final Map<Long, BookProduct> bookProductDatabase = new HashMap<>();

    public InMemoryBookProductRepository() {
        insertTestDataToDb();
    }

    @Override
    public Optional<BookProduct> findById(Long bookId) {
        return Optional.ofNullable(bookProductDatabase.get(bookId));
    }

    private void insertTestDataToDb() {
        bookProductDatabase.put(1L, new BookProduct(
                1,
                new BookTitle("TestTitle1"),
                new BookAuthor("TestAuthor1"),
                new AvailabilityStatus(true),
                new AvailablePieces(65),
                new BookPrice(BigDecimal.valueOf(46.97))
        ));
        bookProductDatabase.put(2L, new BookProduct(
                2,
                new BookTitle("TestTitle1"),
                new BookAuthor("TestAuthor1"),
                new AvailabilityStatus(true),
                new AvailablePieces(65),
                new BookPrice(BigDecimal.valueOf(46.97))
        ));
    }
}
