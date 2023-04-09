package com.bookstoreapplication.bookstore.domain.purchase.value_objects;

import java.io.Serializable;

public record SimpleBookId(

        long bookId

) implements Serializable { }
