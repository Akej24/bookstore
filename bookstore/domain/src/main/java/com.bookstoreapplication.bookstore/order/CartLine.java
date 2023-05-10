package com.bookstoreapplication.bookstore.order;

import com.bookstoreapplication.bookstore.order.exception.NotEnoughBooksInMagazineException;
import com.bookstoreapplication.bookstore.order.value_object.BooksAmount;
import lombok.AllArgsConstructor;
import lombok.Getter;

import javax.persistence.Embedded;
import java.io.Serializable;

@Getter
@AllArgsConstructor
class CartLine implements Serializable {

    private final long customerId;
    private final BookProduct bookProduct;
    private BooksAmount amount;

    public CartLine(long customerId, BookProduct bookProduct) {
        this.customerId = customerId;
        this.bookProduct = bookProduct;
        this.amount = new BooksAmount(1);
    }

    void increaseAmount() {
        if(bookProduct.getAvailablePieces().getAvailablePieces().compareTo(amount.getBooksAmount()) < 1){
            throw new NotEnoughBooksInMagazineException();
        }
        amount = amount.increaseAmount();
    }

    void decreaseAmount() {
        if(bookProduct.getAvailablePieces().getAvailablePieces().compareTo(amount.getBooksAmount()) < 1){
            throw new NotEnoughBooksInMagazineException();
        }
        amount = amount.decreaseAmount();
    }

}
