package com.bookstoreapplication.bookstore.purchase.cart;

import com.bookstoreapplication.bookstore.book.value_object.*;
import com.bookstoreapplication.bookstore.purchase.exception.NotEnoughBooksInMagazineException;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;

import static org.junit.jupiter.api.Assertions.*;

class CartLineTest {

    @Test
    @DisplayName("Should pass when newly created cart line contains properly customer id, book product id and default books amount equals to 1")
    public void createNewCartLine_and_checkFields_and_defaultBooksAmountEqualToOne() {
        CartLine newCartLine = getCartLineInitialState();
        assertEquals(newCartLine.getCustomerId(), 9);
        assertEquals(newCartLine.getBookProduct().getBookId(), 43);
        assertEquals(newCartLine.getAmount().getBooksAmount(), 1);
    }

    @Test
    @DisplayName("Should pass when was able to increase book product in cart amount")
    public void checkIncreaseAmount_and_returnTrue() {
        CartLine newCartLine = getCartLineInitialState();
        assertDoesNotThrow(newCartLine::increaseAmount);
    }

    @Test
    @DisplayName("Should throw NotEnoughBooksInMagazineException when was not able to increase book product in cart amount")
    public void checkIncreaseAmount_and_throwNotEnoughBooksInMagazineException() {
        CartLine newCartLine = getCartLineInitialState();
        newCartLine.increaseAmount();
        newCartLine.increaseAmount();
        assertThrows(NotEnoughBooksInMagazineException.class, newCartLine::increaseAmount);
    }

    @Test
    @DisplayName("Should pass when book product available pieces are higher than customer wanted to order")
    public void checkIsAbleToOrder_and_returnTrue() {
        CartLine newCartLine = getCartLineInitialState();
        newCartLine.increaseAmount();
        newCartLine.increaseAmount();
        assertDoesNotThrow(() -> newCartLine.isAbleToOrder(4));
    }

    @Test
    @DisplayName("Should throw NotEnoughBooksInMagazineException when book product available pieces are lower than customer wanted to order")
    public void checkIsAbleToOrder_and_throwNotEnoughBooksInMagazineException() {
        CartLine newCartLine = getCartLineInitialState();
        newCartLine.increaseAmount();
        newCartLine.increaseAmount();
        assertThrows(NotEnoughBooksInMagazineException.class, () -> newCartLine.isAbleToOrder(2));

    }

    private static CartLine getCartLineInitialState() {
        return new CartLine(9, new BookProduct(
                43,
                new BookTitle("TestTitle"),
                new BookAuthor("TestAuthor"),
                new AvailabilityStatus(true),
                new AvailablePieces(3),
                new BookPrice(BigDecimal.valueOf(41.45))
        ));
    }
}