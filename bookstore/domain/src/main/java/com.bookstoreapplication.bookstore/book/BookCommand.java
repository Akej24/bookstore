package com.bookstoreapplication.bookstore.book;

import com.bookstoreapplication.bookstore.book.value_object.*;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
class BookCommand {

    private Title title;
    private Author author;
    private ReleaseDate releaseDate;
    private NumberOfPages numberOfPages;
    private AvailabilityStatus availabilityStatus;
    private AvailablePieces availablePieces;
    private Price price;

}
