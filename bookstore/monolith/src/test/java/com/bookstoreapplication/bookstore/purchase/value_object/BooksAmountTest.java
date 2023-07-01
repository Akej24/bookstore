package com.bookstoreapplication.bookstore.purchase.value_object;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class BooksAmountTest {

    @Test
    @DisplayName("Should pass when increased amount is one higher than initial amount")
    void increaseAmount() {
        BooksAmount initialBooksAmount = new BooksAmount(5);
        assertEquals(initialBooksAmount.increaseAmount().getBooksAmount(), 6);
    }

    @Test
    @DisplayName("Should pass when decreased amount is one less than initial amount")
    void decreaseAmount() {
        BooksAmount initialBooksAmount = new BooksAmount(9);
        assertEquals(initialBooksAmount.decreaseAmount().getBooksAmount(), 8);
    }
}