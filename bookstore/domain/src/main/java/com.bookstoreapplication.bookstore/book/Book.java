package com.bookstoreapplication.bookstore.book;

import com.bookstoreapplication.bookstore.book.value_object.*;
import com.bookstoreapplication.bookstore.purchase.exception.NotEnoughBooksInMagazineException;
import com.bookstoreapplication.bookstore.purchase.value_object.BooksAmount;
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
    private BookTitle bookTitle;
    @Embedded
    private BookAuthor bookAuthor;
    @Embedded
    private ReleaseDate releaseDate;
    @Embedded
    private NumberOfPages numberOfPages;
    @Embedded
    private AvailabilityStatus availabilityStatus;
    @Embedded
    private AvailablePieces availablePieces;
    @Embedded
    private BookPrice bookPrice;
    @Embedded
    private BookAudit bookAudit;

    public Book(BookCommand source) {
        bookTitle = source.bookTitle();
        bookAuthor = source.bookAuthor();
        releaseDate = source.releaseDate();
        numberOfPages = source.numberOfPages();
        availabilityStatus = source.availabilityStatus();
        availablePieces = source.availablePieces();
        bookPrice = source.bookPrice();
        this.bookAudit = new BookAudit();
        updateAvailability();
    }

    public Book update(BookCommand source){
        bookTitle = source.bookTitle();
        bookAuthor = source.bookAuthor();
        releaseDate = source.releaseDate();
        numberOfPages = source.numberOfPages();
        availabilityStatus = source.availabilityStatus();
        availablePieces = source.availablePieces();
        bookPrice = source.bookPrice();
        updateAvailability();
        return this;
    }

    void updateAvailability() {
        if (availabilityStatus.getAvailabilityStatus().equals(false) || availablePieces.getAvailablePieces() == 0) {
            availablePieces = new AvailablePieces(0);
            availabilityStatus = new AvailabilityStatus(false);
        }
    }

    Book decreaseAvailablePieces(BooksAmount booksAmount){
        int newAvailablePieces = availablePieces.getAvailablePieces() - booksAmount.getBooksAmount();
        if(newAvailablePieces == 0){
            toggleStatusIfPiecesZero();
        }else if(newAvailablePieces < 0 ){
            throw new NotEnoughBooksInMagazineException();
        }
        availablePieces = new AvailablePieces(newAvailablePieces);
        return this;
    }

    void toggleStatusIfPiecesZero(){
        availabilityStatus = new AvailabilityStatus(!availabilityStatus.getAvailabilityStatus());
    }

}
