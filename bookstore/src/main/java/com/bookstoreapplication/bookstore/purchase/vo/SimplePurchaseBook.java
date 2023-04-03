package com.bookstoreapplication.bookstore.purchase.vo;

import com.bookstoreapplication.bookstore.book.vo.AvailabilityStatus;
import com.bookstoreapplication.bookstore.book.vo.AvailablePieces;
import com.bookstoreapplication.bookstore.book.vo.Price;
import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;

import javax.persistence.Table;
import java.io.Serializable;

@Table(name = "books")
@Getter
@AllArgsConstructor
@EqualsAndHashCode
public class SimplePurchaseBook implements Serializable {

    private SimpleBookId bookId;
    private AvailabilityStatus availabilityStatus;
    private AvailablePieces availablePieces;
    private Price price;

}
