package com.bookstoreapplication.bookstore.book.vo;

import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import java.io.Serializable;

public record BookId(

        @GeneratedValue(strategy = GenerationType.IDENTITY)
        long bookId

) implements Serializable { }
