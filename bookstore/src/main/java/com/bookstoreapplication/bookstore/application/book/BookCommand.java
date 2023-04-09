package com.bookstoreapplication.bookstore.domain.book.core;

import com.bookstoreapplication.bookstore.domain.book.value_object.*;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class BookCommand {

    private Title title;
    private Author author;
    private ReleaseDate releaseDate;
    private NumberOfPages numberOfPages;
    private AvailabilityStatus status;
    private AvailablePieces availablePieces;
    private Price price;

}
