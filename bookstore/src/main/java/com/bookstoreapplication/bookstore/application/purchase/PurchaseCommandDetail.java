package com.bookstoreapplication.bookstore.application.purchase;

import com.bookstoreapplication.bookstore.domain.purchase.value_object.BooksAmount;
import com.bookstoreapplication.bookstore.domain.purchase.value_object.SimpleBookId;

import java.io.Serializable;

public record PurchaseCommandDetail(

        SimpleBookId bookId,
        BooksAmount booksAmount

) implements Serializable { }
