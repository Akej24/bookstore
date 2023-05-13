package com.bookstoreapplication.bookstore.order;

import com.bookstoreapplication.bookstore.order.exception.BookProductNotFoundException;
import com.bookstoreapplication.bookstore.order.value_object.TotalPrice;
import lombok.AllArgsConstructor;
import lombok.Getter;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Getter
@AllArgsConstructor
class Cart implements Serializable {

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

    List<OrderDetail> mapToOrderDetails(long orderId){
        return this.getCartLines()
                .stream()
                .map(line -> new OrderDetail(line, orderId))
                .toList();
    }

    CheckoutCart checkout(){
        return new CheckoutCart(this);
    }

}
