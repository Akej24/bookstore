package com.bookstoreapplication.bookstore.purchase;

import com.bookstoreapplication.bookstore.book.BookDto;
import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
class PurchaseDetailResponse {

    private BookDto book;
    private Integer booksAmount;

}
