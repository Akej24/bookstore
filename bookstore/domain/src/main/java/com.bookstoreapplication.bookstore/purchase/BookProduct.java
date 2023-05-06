package com.bookstoreapplication.bookstore.purchase;

import com.bookstoreapplication.bookstore.book.value_object.AvailabilityStatus;
import com.bookstoreapplication.bookstore.book.value_object.AvailablePieces;
import com.bookstoreapplication.bookstore.book.value_object.Price;
import com.bookstoreapplication.bookstore.purchase.exception.NotEnoughBooksInMagazineException;
import com.bookstoreapplication.bookstore.purchase.value_object.BooksAmount;
import lombok.*;

import java.io.Serializable;
import java.math.BigDecimal;
import java.math.RoundingMode;

@Getter
@AllArgsConstructor(access = AccessLevel.PROTECTED)
class BookProduct implements Serializable {

    private long bookId;
    private AvailabilityStatus availabilityStatus;
    private AvailablePieces availablePieces;
    private Price price;

    void decreaseAvailablePieces(BooksAmount booksAmount){
        int newAvailablePieces = availablePieces.getAvailablePieces() - booksAmount.getBooksAmount();
        if(newAvailablePieces == 0){
            toggleStatusIfPiecesZero();
        }else if(newAvailablePieces < 0 ){
            throw new NotEnoughBooksInMagazineException();
        }
        availablePieces = new AvailablePieces(newAvailablePieces);
    }

    void toggleStatusIfPiecesZero(){
        availabilityStatus = new AvailabilityStatus(!availabilityStatus.getAvailabilityStatus());
    }

}
