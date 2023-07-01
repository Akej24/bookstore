package com.bookstoreapplication.bookstore.purchase.cart;

import com.bookstoreapplication.bookstore.purchase.exception.BookProductNotFoundException;
import com.bookstoreapplication.bookstore.purchase.exception.BookProductAlreadyExistsInCartException;
import com.bookstoreapplication.bookstore.purchase.exception.NotEnoughBooksInMagazineException;
import com.bookstoreapplication.bookstore.purchase.value_object.TotalPrice;
import lombok.AllArgsConstructor;
import lombok.Getter;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Getter
@AllArgsConstructor
public class Cart implements Serializable {

    private long customerId;
    private List<CartLine> cartLines;
    private TotalPrice totalPrice;

    public Cart(long customerId, BookProduct bookProduct) {
        checkBookIsAvailableToAdd(bookProduct);
        this.customerId = customerId;
        var cartLines = new ArrayList<CartLine>();
        cartLines.add(new CartLine(customerId, bookProduct));
        this.cartLines = cartLines;
        this.totalPrice = calculateTotalPrice();
    }

    Cart addProduct(BookProduct bookProduct) {
        if (cartLines.stream().anyMatch(cartLine -> cartLine.getBookProduct().getBookId() == bookProduct.getBookId())) {
            throw new BookProductAlreadyExistsInCartException();
        }
        checkBookIsAvailableToAdd(bookProduct);
        cartLines.add(new CartLine(customerId, bookProduct));
        this.totalPrice = calculateTotalPrice();
        return this;
    }

    Cart deleteProduct(long bookId){
        cartLines.removeIf(cartLine -> cartLine.getBookProduct().getBookId() == bookId);
        this.totalPrice = calculateTotalPrice();
        return this;
    }

    Cart increaseProductAmount(BookProduct bookProduct) {
        Optional<CartLine> cartLineOptional = findBookProductInCartLines(bookProduct);
        if (cartLineOptional.isPresent()) {
            cartLineOptional.get().increaseAmount();
            this.totalPrice = calculateTotalPrice();
            return this;
        } else {
            throw new BookProductNotFoundException();
        }
    }

    Cart decreaseProductAmount(BookProduct bookProduct){
        Optional<CartLine> cartLineOptional = findBookProductInCartLines(bookProduct);
        if (cartLineOptional.isPresent()) {
            cartLineOptional.get().decreaseAmount();
            this.totalPrice = calculateTotalPrice();
            return this;
        } else {
            throw new BookProductNotFoundException();
        }
    }

    private Optional<CartLine> findBookProductInCartLines(BookProduct bookProduct) {
        return cartLines.stream()
                .filter(cartLine -> cartLine.getBookProduct().getBookId() == bookProduct.getBookId())
                .findFirst();
    }

    private static void checkBookIsAvailableToAdd(BookProduct bookProduct) {
        if (bookProduct.getAvailablePieces().getAvailablePieces() < 1 || !bookProduct.getAvailabilityStatus().getAvailabilityStatus()) {
            throw new NotEnoughBooksInMagazineException();
        }
    }

    private TotalPrice calculateTotalPrice() {
        return new TotalPrice(cartLines.stream().map( line -> line.getBookProduct()
                .getBookPrice().getBookPrice()
                .multiply(BigDecimal.valueOf(line.getAmount().getBooksAmount())))
                .reduce(BigDecimal.ZERO, BigDecimal::add));
    }
}
