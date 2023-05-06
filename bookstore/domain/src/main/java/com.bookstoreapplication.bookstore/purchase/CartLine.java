package com.bookstoreapplication.bookstore.purchase;

import com.bookstoreapplication.bookstore.purchase.exception.NotEnoughBooksInMagazineException;
import com.bookstoreapplication.bookstore.purchase.value_object.BooksAmount;
import lombok.Getter;

import java.io.Serializable;

@Getter
class CartLine implements Serializable {

    private final BookProduct bookProduct;
    private BooksAmount amount;

    public CartLine(BookProduct bookProduct) {
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
