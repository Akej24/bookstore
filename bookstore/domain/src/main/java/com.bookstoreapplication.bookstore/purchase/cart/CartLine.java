package com.bookstoreapplication.bookstore.purchase.cart;

import com.bookstoreapplication.bookstore.purchase.exception.NotEnoughBooksInMagazineException;
import com.bookstoreapplication.bookstore.purchase.value_object.BooksAmount;
import lombok.AllArgsConstructor;
import lombok.Getter;

import java.io.Serializable;

@Getter
@AllArgsConstructor
public class CartLine implements Serializable {

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

    public void isAbleToOrder(Integer availablePieces){
        if(this.getAmount().getBooksAmount() > availablePieces){
            throw new NotEnoughBooksInMagazineException();
        }
    }

    void decreaseAmount() {
        amount = amount.decreaseAmount();
    }
}
