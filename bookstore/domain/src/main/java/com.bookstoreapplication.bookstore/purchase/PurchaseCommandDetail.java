package com.bookstoreapplication.bookstore.purchase;

import com.bookstoreapplication.bookstore.purchase.value_object.BooksAmount;
import com.bookstoreapplication.bookstore.purchase.value_object.SimpleBookId;

import java.io.Serializable;

public record PurchaseCommandDetail(

        SimpleBookId bookId,
        BooksAmount booksAmount

) implements Serializable { }
