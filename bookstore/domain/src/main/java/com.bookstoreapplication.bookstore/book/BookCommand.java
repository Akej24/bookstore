package com.bookstoreapplication.bookstore.book;

import com.bookstoreapplication.bookstore.book.value_object.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

import javax.validation.Valid;

@Getter
@AllArgsConstructor
class BookCommand {

    @Valid
    private BookTitle bookTitle;
    @Valid
    private BookAuthor bookAuthor;
    @Valid
    private ReleaseDate releaseDate;
    @Valid
    private NumberOfPages numberOfPages;
    @Valid
    private AvailabilityStatus availabilityStatus;
    @Valid
    private AvailablePieces availablePieces;
    @Valid
    private BookPrice bookPrice;

}
