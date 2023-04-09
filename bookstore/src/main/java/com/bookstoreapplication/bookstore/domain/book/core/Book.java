package com.bookstoreapplication.bookstore.domain.book;

import com.bookstoreapplication.bookstore.book.vo.*;
import com.bookstoreapplication.bookstore.domain.book.vo.*;
import com.bookstoreapplication.bookstore.domain.purchase.book.vo.*;
import lombok.*;

import javax.persistence.*;

@Entity
@Table(name = "books")
@Getter
@Setter
@NoArgsConstructor
class Book {

    @Id
    private BookId bookId;
    private Title title;
    private Author author;
    private ReleaseDate releaseDate;
    private NumberOfPages numberOfPages;
    private AvailabilityStatus availabilityStatus;
    private AvailablePieces availablePieces;
    private Price price;
    @Embedded
    private BookAudit bookAudit;

    public void updateAvailability() {
        if (availabilityStatus.status().equals(false) || availablePieces.availablePieces() == 0) {
            availablePieces = new AvailablePieces(0);
            availabilityStatus = new AvailabilityStatus(false);
        }
    }

}
