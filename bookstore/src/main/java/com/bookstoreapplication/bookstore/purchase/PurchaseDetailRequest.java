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

    @Min(value = 0, message = "Book id must not be null and the minimum value is 0")
    @NotNull
    Long bookId;

    @Min(value = 1, message = "Books amount must not be null and the minimum value is 1")
    @NotNull
    Integer booksAmount;

}
