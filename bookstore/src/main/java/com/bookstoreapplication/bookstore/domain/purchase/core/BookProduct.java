package com.bookstoreapplication.bookstore.domain.purchase.core;

import com.bookstoreapplication.bookstore.domain.book.vo.AvailabilityStatus;
import com.bookstoreapplication.bookstore.domain.book.vo.AvailablePieces;
import com.bookstoreapplication.bookstore.domain.book.vo.Price;
import com.bookstoreapplication.bookstore.domain.purchase.value_objects.BooksAmount;
import com.bookstoreapplication.bookstore.domain.purchase.value_objects.SimpleBookId;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;

import javax.persistence.Id;
import java.io.Serializable;
import java.math.BigDecimal;
import java.math.RoundingMode;

@Getter
@AllArgsConstructor(access = AccessLevel.PROTECTED)
@EqualsAndHashCode
public class OrderedBook implements Serializable {

    @Id
    private SimpleBookId bookId;
    private AvailabilityStatus availabilityStatus;
    private AvailablePieces availablePieces;
    private Price price;

    void decreaseAvailablePieces(BooksAmount booksAmount){
        int newAvailablePieces = availablePieces.availablePieces() - booksAmount.booksAmount();
        if(newAvailablePieces == 0){
            toggleStatusIfPiecesZero();
        }else if(newAvailablePieces < 0 ){
            throw new IllegalArgumentException("Not enough books in magazine to buy them");
        }
        availablePieces = new AvailablePieces(newAvailablePieces);
    }

    void toggleStatusIfPiecesZero(){
        availabilityStatus = new AvailabilityStatus(!availabilityStatus.status());
    }

    BigDecimal addToTotalPrice(BigDecimal totalPrice, BooksAmount booksAmount) {
        BigDecimal toAdd = price.price()
                .multiply(BigDecimal.valueOf(booksAmount.booksAmount()))
                .setScale(2, RoundingMode.HALF_UP);
        totalPrice = totalPrice.add(toAdd);
        return totalPrice;
    }

}
