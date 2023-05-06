package com.bookstoreapplication.bookstore.purchase;

import java.util.Optional;

interface BookProductRepository{

    Optional<BookProduct> findById(Long bookId);

}
