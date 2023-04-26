package com.bookstoreapplication.bookstore.book;

import com.bookstoreapplication.bookstore.book.value_object.*;
import lombok.*;

import javax.persistence.*;
import java.io.Serializable;

@Table(name = "books")
@Entity
@Getter
@AllArgsConstructor
@NoArgsConstructor
class Book implements Serializable {

    @EmbeddedId
    private BookId bookId;
    @Embedded
    private Title title;
    @Embedded
    private Author author;
    @Embedded
    private ReleaseDate releaseDate;
    @Embedded
    private NumberOfPages numberOfPages;
    @Embedded
    private AvailabilityStatus availabilityStatus;
    @Embedded
    private AvailablePieces availablePieces;
    @Embedded
    private Price price;
    @Embedded
    private BookAudit bookAudit;

    public Book(BookCommand source) {
        bookId = new BookId();
        title = source.getTitle();
        author = source.getAuthor();
        releaseDate = source.getReleaseDate();
        numberOfPages = source.getNumberOfPages();
        availabilityStatus = source.getAvailabilityStatus();
        availablePieces = source.getAvailablePieces();
        price = source.getPrice();
        this.bookAudit = new BookAudit();
        updateAvailability();
    }

    public Book update(BookCommand source){
        title = source.getTitle();
        author = source.getAuthor();
        releaseDate = source.getReleaseDate();
        numberOfPages = source.getNumberOfPages();
        availabilityStatus = source.getAvailabilityStatus();
        availablePieces = source.getAvailablePieces();
        price = source.getPrice();
        updateAvailability();
        return this;
    }

    void updateAvailability() {
        if (availabilityStatus.getAvailabilityStatus().equals(false) || availablePieces.getAvailablePieces() == 0) {
            availablePieces = new AvailablePieces(0);
            availabilityStatus = new AvailabilityStatus(false);
        }
    }

}
