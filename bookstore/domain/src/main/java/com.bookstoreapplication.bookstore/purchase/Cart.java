package com.bookstoreapplication.bookstore.purchase;

import com.bookstoreapplication.bookstore.purchase.exception.BookProductNotFoundException;
import com.bookstoreapplication.bookstore.purchase.value_object.TotalPrice;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

@Getter
@AllArgsConstructor(access = AccessLevel.PROTECTED)
class Cart implements Serializable {

    private long customerId;
    private List<CartLine> cartLines;
    private TotalPrice totalPrice;

    public Cart(long customerId, BookProduct bookProduct) {
        this.customerId = customerId;
        var cartLines = new ArrayList<CartLine>();
        cartLines.add(new CartLine(bookProduct));
        this.cartLines = cartLines;
        this.totalPrice = calculateTotalPrice();
    }

    Cart addProduct(BookProduct bookProduct) {
        cartLines.add(new CartLine(bookProduct));
        this.totalPrice = calculateTotalPrice();
        return this;
    }

    Cart deleteProduct(BookProduct bookProduct){
        cartLines.removeIf(cartLine -> cartLine.getBookProduct().getBookId() == bookProduct.getBookId());
        this.totalPrice = calculateTotalPrice();
        return this;
    }

    Cart increaseProductAmount(BookProduct bookProduct){
        cartLines.stream()
                .filter(cartLine -> cartLine.getBookProduct().equals(bookProduct))
                .findFirst()
                .ifPresentOrElse(CartLine::increaseAmount, BookProductNotFoundException::new);
        this.totalPrice = calculateTotalPrice();
        return this;
    }

    Cart decreaseProductAmount(BookProduct bookProduct){
        cartLines.stream()
                .filter(cartLine -> cartLine.getBookProduct().equals(bookProduct))
                .findFirst()
                .ifPresentOrElse(CartLine::decreaseAmount, BookProductNotFoundException::new);
        this.totalPrice = calculateTotalPrice();
        return this;
    }

    private TotalPrice calculateTotalPrice() {
        return new TotalPrice(cartLines.stream().map( line -> line.getBookProduct()
                .getPrice().getPrice()
                .multiply(BigDecimal.valueOf(line.getAmount().getBooksAmount())))
                .reduce(BigDecimal.ZERO, BigDecimal::add));
    }

    CheckoutCart checkout(){
        return new CheckoutCart(this);
    }

}
