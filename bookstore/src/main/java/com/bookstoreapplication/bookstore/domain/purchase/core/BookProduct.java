package com.bookstoreapplication.bookstore.domain.purchase.core;

import com.bookstoreapplication.bookstore.domain.book.value_object.AvailabilityStatus;
import com.bookstoreapplication.bookstore.domain.book.value_object.AvailablePieces;
import com.bookstoreapplication.bookstore.domain.book.value_object.Price;
import com.bookstoreapplication.bookstore.domain.purchase.exception.NotEnoughBooksInMagazineException;
import com.bookstoreapplication.bookstore.domain.purchase.value_object.BooksAmount;
import com.bookstoreapplication.bookstore.domain.purchase.value_object.SimpleBookId;
import lombok.*;

import javax.persistence.*;
import java.io.Serializable;
import java.math.BigDecimal;
import java.math.RoundingMode;

@Table(name="books")
@Entity
@Getter
@AllArgsConstructor(access = AccessLevel.PROTECTED)
@NoArgsConstructor
public class BookProduct implements Serializable {

    @EmbeddedId
    private SimpleBookId bookId;
    @Embedded
    private AvailabilityStatus availabilityStatus;
    @Embedded
    private AvailablePieces availablePieces;
    @Embedded
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
        availabilityStatus = new AvailabilityStatus(!availabilityStatus.getStatus());
    }

    BigDecimal addToTotalPrice(BigDecimal totalPrice, BooksAmount booksAmount) {
        BigDecimal toAdd = price.getPrice()
                .multiply(BigDecimal.valueOf(booksAmount.getBooksAmount()))
                .setScale(2, RoundingMode.HALF_UP);
        totalPrice = totalPrice.add(toAdd);
        return totalPrice;
    }

}
