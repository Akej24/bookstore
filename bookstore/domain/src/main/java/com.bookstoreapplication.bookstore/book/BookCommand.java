package com.bookstoreapplication.bookstore.book;

import com.bookstoreapplication.bookstore.book.value_object.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.validation.Valid;

@Getter
@NoArgsConstructor
@AllArgsConstructor
class BookCommand {

    @Valid
    private Title title;
    @Valid
    private Author author;
    @Valid
    private ReleaseDate releaseDate;
    @Valid
    private NumberOfPages numberOfPages;
    @Valid
    private AvailabilityStatus availabilityStatus;
    @Valid
    private AvailablePieces availablePieces;
    @Valid
    private Price price;

}
