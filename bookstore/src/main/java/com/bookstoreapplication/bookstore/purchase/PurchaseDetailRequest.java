package com.bookstoreapplication.bookstore.purchase;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;

@Getter
@NoArgsConstructor
@AllArgsConstructor
class PurchaseDetailRequest {

    @Min(value = 0, message = "The minimum value of bookId is 0")
    @NotNull(message = "Book id must not be null")
    Long bookId;

    @Min(value = 1, message = "The minimum value of books amount is 1")
    @NotNull(message = "Books amount must not be null")
    Integer booksAmount;

}
