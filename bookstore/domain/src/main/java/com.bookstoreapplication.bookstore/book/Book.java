package com.bookstoreapplication.bookstore.book;

import com.bookstoreapplication.bookstore.book.value_object.*;
import com.bookstoreapplication.bookstore.order.exception.NotEnoughBooksInMagazineException;
import com.bookstoreapplication.bookstore.order.value_object.BooksAmount;
import lombok.*;

import javax.persistence.*;
import java.io.Serializable;

@Table(name = "books")
@Entity
@Getter
@NoArgsConstructor
@AllArgsConstructor
class Book implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long bookId;
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
