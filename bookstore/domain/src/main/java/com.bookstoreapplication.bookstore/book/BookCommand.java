package com.bookstoreapplication.bookstore.book;

import com.bookstoreapplication.bookstore.book.value_object.*;

import javax.validation.Valid;

record BookCommand(

        @Valid BookTitle bookTitle,
        @Valid BookAuthor bookAuthor,
        @Valid ReleaseDate releaseDate,
        @Valid NumberOfPages numberOfPages,
        @Valid AvailabilityStatus availabilityStatus,
        @Valid AvailablePieces availablePieces,
        @Valid BookPrice bookPrice

) { }
