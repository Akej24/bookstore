package com.bookstoreapplication.bookstore.order;

import java.util.Optional;

interface BookProductRepository{

    Optional<BookProduct> findById(Long bookId);

}
