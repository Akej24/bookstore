package com.bookstoreapplication.bookstore.purchase.cart;

import com.bookstoreapplication.bookstore.book.value_object.*;
import com.bookstoreapplication.bookstore.purchase.exception.BookProductAlreadyExistsInCartException;
import com.bookstoreapplication.bookstore.purchase.exception.NotEnoughBooksInMagazineException;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;

import static org.junit.jupiter.api.Assertions.*;

class CartTest {

    @Test
    @DisplayName("Should throw NotEnoughBooksInMagazineException when book product amount is less than 0 or availability status is false")
    void createNewCart_and_throwNotEnoughBooksInMagazineException() {
        assertThrows(NotEnoughBooksInMagazineException.class, () -> new Cart(4, getNotAvailableBook()));
        assertThrows(NotEnoughBooksInMagazineException.class, () -> new Cart(9, getZeroAmountBook()));
    }

    @Test
    @DisplayName("Should throw BookProductAlreadyExistsInCartException when try to add to cart already added book")
    void tryAddExistingProduct_and_throwBookProductAlreadyExistsInCartException() {
        Cart cart = new Cart(75, getFirstBookInitialState());
        assertThrows(BookProductAlreadyExistsInCartException.class, () -> cart.addProduct(getFirstBookInitialState()));
    }

    @Test
    @DisplayName("Should pass when successfully calculated total price from added two products")
    void createNewCart_and_addNextProduct_and_calculateTotalPrice() {
        Cart cart = new Cart(54, getFirstBookInitialState());
        cart.addProduct(getSecondBookInitialState());
        double expectedPrice = getFirstBookInitialState().getBookPrice().getBookPrice().doubleValue()
                + getSecondBookInitialState().getBookPrice().getBookPrice().doubleValue();
        assertEquals(cart.getTotalPrice().getTotalPrice().doubleValue(), expectedPrice);
    }

    @Test
    @DisplayName("Should pass when two cart products after one remove are equal to one")
    void createNewCart_and_addNextProduct_and_removeIt() {
        Cart cart = new Cart(54, getFirstBookInitialState());
        cart.addProduct(getSecondBookInitialState());
        cart.deleteProduct(getSecondBookInitialState().getBookId());
        assertEquals(1, cart.getCartLines().size());
    }

    @Test
    @DisplayName("Should pass when total price is equals to twice the previous price after one book product cart increment")
    void createNewCart_and_increaseProductAmount_and_calculateTotalPrice() {
        Cart cart = new Cart(54, getFirstBookInitialState());
        cart.increaseProductAmount(getFirstBookInitialState());
        assertEquals(getFirstBookInitialState().getBookPrice().getBookPrice().multiply(BigDecimal.valueOf(2)).doubleValue(), cart.getTotalPrice().getTotalPrice().doubleValue());
    }

    private static BookProduct getFirstBookInitialState() {
        return new BookProduct(
                12,
                new BookTitle("TestTitle1"),
                new BookAuthor("TestAuthor1"),
                new AvailabilityStatus(true),
                new AvailablePieces(65),
                new BookPrice(BigDecimal.valueOf(46.97))
        );
    }

    private static BookProduct getSecondBookInitialState() {
        return new BookProduct(
                234,
                new BookTitle("TestTitle2"),
                new BookAuthor("TestAuthor2"),
                new AvailabilityStatus(true),
                new AvailablePieces(4),
                new BookPrice(BigDecimal.valueOf(83.63))
        );
    }

    private static BookProduct getNotAvailableBook() {
        return new BookProduct(
                524,
                new BookTitle("TestTitle"),
                new BookAuthor("TestAuthor"),
                new AvailabilityStatus(false),
                new AvailablePieces(43),
                new BookPrice(BigDecimal.valueOf(43.00))
        );
    }

    private static BookProduct getZeroAmountBook() {
        return new BookProduct(
                9,
                new BookTitle("TestTitle"),
                new BookAuthor("TestAuthor"),
                new AvailabilityStatus(true),
                new AvailablePieces(0),
                new BookPrice(BigDecimal.valueOf(43.00))
        );
    }
}