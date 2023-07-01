package com.bookstoreapplication.bookstore.purchase.cart;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;

class CartHandlerTest {

    private InMemoryCartRepository inMemoryCartRepository;
    private CartHandler testCartHandler;

    @BeforeEach
    public void setup() {
        inMemoryCartRepository = new InMemoryCartRepository();
        InMemoryBookProductRepository inMemoryBookProductRepository = new InMemoryBookProductRepository();
        testCartHandler = new CartHandler(inMemoryCartRepository, inMemoryBookProductRepository);
    }

    @Test
    @DisplayName("Should pass when successfully added one customer cart")
    void addOneProductToCart_and_checkIsPresent() {
        testCartHandler.addProduct(1, 1);
        assertThat(inMemoryCartRepository.findByCustomerId(1)).isPresent();
    }

    @Test
    @DisplayName("Should pass when added two books to one customer cart and all cart records are equal to one")
    void addTwoProductsToCart_and_checkAllCartsAreEqualToOne() {
        testCartHandler.addProduct(1, 1);
        testCartHandler.addProduct(1, 2);
        assertEquals(1, inMemoryCartRepository.countAllRecords());
    }

    @Test
    @DisplayName("Should pass when added two books to different customer carts and all cart records are equal to two")
    void addTwoDifferentProducts_and_checkAllCartsAreEqualToTwo() {
        testCartHandler.addProduct(1, 1);
        testCartHandler.addProduct(2, 1);
        assertEquals(2, inMemoryCartRepository.countAllRecords());
    }

    @Test
    @DisplayName("Should pass when added two books, deleted one and all cart books are equal to one")
    void deleteOneOfTwoCartProducts_and_checkContainsOneProduct() {
        testCartHandler.addProduct(1, 1);
        testCartHandler.addProduct(1, 2);
        testCartHandler.deleteProduct(1, 2);
        assertEquals(1, inMemoryCartRepository.findByCustomerId(1).get().getCartLines().size());
    }

    @Test
    @DisplayName("Should pass when added one book, deleted it and all records now are equal to zero")
    void deleteOneOfOneCartProduct_and_checkCartDeleted() {
        testCartHandler.addProduct(1, 1);
        testCartHandler.deleteProduct(1, 1);
        assertEquals(0, inMemoryCartRepository.countAllRecords());
    }

    @Test
    @DisplayName("Should pass when cart created and next removed and all records now are equal to zero")
    void cleanCart_and_checkIfRemoved() {
        testCartHandler.addProduct(1, 1);
        testCartHandler.cleanCart(1);
        assertEquals(0, inMemoryCartRepository.countAllRecords());
    }

}