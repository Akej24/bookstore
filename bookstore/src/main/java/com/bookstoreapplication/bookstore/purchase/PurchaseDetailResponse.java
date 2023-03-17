package com.bookstoreapplication.bookstore.purchase;

import com.bookstoreapplication.bookstore.book.BookResponse;
import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
class PurchaseDetailResponse {

    private BookResponse book;
    private Integer booksAmount;

}
