package com.bookstoreapplication.bookstore.purchase.checkout_cart;

import com.bookstoreapplication.bookstore.book.value_object.*;
import com.bookstoreapplication.bookstore.purchase.cart.BookProduct;
import com.bookstoreapplication.bookstore.purchase.cart.Cart;
import com.bookstoreapplication.bookstore.purchase.exception.MissingDeliveryDetailsInCheckoutCartException;
import com.bookstoreapplication.bookstore.purchase.exception.MissingPaymentMethodInCheckoutCartException;
import com.bookstoreapplication.bookstore.purchase.exception.NotEnoughDataToPayAndDeliverException;
import com.bookstoreapplication.bookstore.purchase.value_object.*;
import com.bookstoreapplication.bookstore.user.value_objects.FirstName;
import com.bookstoreapplication.bookstore.user.value_objects.LastName;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;

import static org.junit.jupiter.api.Assertions.*;

class CheckoutCartTest {

    @Test
    @DisplayName("Should throw NotEnoughDataToPayAndDeliverException when address and payment have not been added")
    void createNewCheckoutCart_and_checkEnoughDataToPayAndDeliver() {
        Cart cart = getCartInitialState();
        CheckoutCart checkoutCart = new CheckoutCart(cart);
        assertThrows(NotEnoughDataToPayAndDeliverException.class, checkoutCart::checkEnoughDataToPayAndDeliver);
    }

    @Test
    @DisplayName("Should throw MissingPaymentMethodInCheckoutCartException when payment has not been added")
    void createNewCheckoutCart_and_addAddress_and_checkEnoughDataToPayAndDeliver() {
        Cart cart = getCartInitialState();
        CheckoutCart checkoutCart = new CheckoutCart(cart);
        checkoutCart.updateAddress(getAddressInitialState());
        assertThrows(MissingPaymentMethodInCheckoutCartException.class, checkoutCart::checkEnoughDataToPayAndDeliver);
    }

    @Test
    @DisplayName("Should throw MissingDeliveryDetailsInCheckoutCartException when address has not been added")
    void createNewCheckoutCart_and_addPayment_and_checkEnoughDataToPayAndDeliver() {
        Cart cart = getCartInitialState();
        CheckoutCart checkoutCart = new CheckoutCart(cart);
        checkoutCart.updatePaymentMethod(PaymentMethod.BLIK);
        assertThrows(MissingDeliveryDetailsInCheckoutCartException.class, checkoutCart::checkEnoughDataToPayAndDeliver);
    }

    @Test
    @DisplayName("Should pass when address and payment have been added")
    void createNewCheckoutCart_and_addPayment_and_addAddress_and_checkEnoughDataToPayAndDeliver() {
        Cart cart = getCartInitialState();
        CheckoutCart checkoutCart = new CheckoutCart(cart);
        checkoutCart.updatePaymentMethod(PaymentMethod.BLIK);
        checkoutCart.updateAddress(getAddressInitialState());
        assertDoesNotThrow(checkoutCart::checkEnoughDataToPayAndDeliver);
    }

    private static Address getAddressInitialState() {
        return new Address(
                new FirstName("TestFirstName"),
                new LastName("TestLastName"),
                new PhoneNumber("111-222-333"),
                new Street("TestStreet"),
                new StreetNumber(6),
                new ZipCode("43-751"),
                new City("TestCity")
        );
    }

    private static Cart getCartInitialState() {
        return new Cart(43, new BookProduct(
                12,
                new BookTitle("TestTitle1"),
                new BookAuthor("TestAuthor1"),
                new AvailabilityStatus(true),
                new AvailablePieces(65),
                new BookPrice(BigDecimal.valueOf(46.97))
        ));
    }
}