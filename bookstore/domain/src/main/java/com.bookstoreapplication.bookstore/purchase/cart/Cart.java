package com.bookstoreapplication.bookstore.purchase.cart;

import com.bookstoreapplication.bookstore.purchase.exception.BookProductNotFoundException;
import com.bookstoreapplication.bookstore.purchase.exception.BookProductAlreadyExistsInCartException;
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
        cartLines.add(new CartLine(customerId, bookProduct));
        this.totalPrice = calculateTotalPrice();
        return this;
    }

    Cart deleteProduct(BookProduct bookProduct){
        cartLines.removeIf(cartLine -> cartLine.getBookProduct().getBookId() == bookProduct.getBookId());
        this.totalPrice = calculateTotalPrice();
        return this;
    }

    Cart increaseProductAmount(BookProduct bookProduct) {
        Optional<CartLine> cartLineOptional = cartLines.stream()
                .filter(cartLine -> cartLine.getBookProduct().getBookId() == bookProduct.getBookId())
                .findFirst();
        if (cartLineOptional.isPresent()) {
            cartLineOptional.get().increaseAmount();
            this.totalPrice = calculateTotalPrice();
            return this;
        } else {
            throw new BookProductNotFoundException();
        }
    }

    Cart decreaseProductAmount(BookProduct bookProduct){
        Optional<CartLine> cartLineOptional = cartLines.stream()
                .filter(cartLine -> cartLine.getBookProduct().getBookId() == bookProduct.getBookId())
                .findFirst();
        if (cartLineOptional.isPresent()) {
            cartLineOptional.get().decreaseAmount();
            this.totalPrice = calculateTotalPrice();
            return this;
        } else {
            throw new BookProductNotFoundException();
        }
    }

    private TotalPrice calculateTotalPrice() {
        return new TotalPrice(cartLines.stream().map( line -> line.getBookProduct()
                .getBookPrice().getBookPrice()
                .multiply(BigDecimal.valueOf(line.getAmount().getBooksAmount())))
                .reduce(BigDecimal.ZERO, BigDecimal::add));
    }





}
