package com.bookstoreapplication.bookstore.purchase.cart;

import com.bookstoreapplication.bookstore.book.value_object.*;
import lombok.*;

import javax.persistence.*;

@Table(name = "books")
@Entity
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class BookProduct {

    @Id
    private long bookId;
    @Embedded
    private BookTitle bookTitle;
    @Embedded
    private BookAuthor bookAuthor;
    @Embedded
    private AvailabilityStatus availabilityStatus;
    @Embedded
    private AvailablePieces availablePieces;
    @Embedded
    private BookPrice bookPrice;

}
