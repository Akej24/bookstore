package com.bookstoreapplication.bookstore.purchase.cart;

import java.util.Optional;

interface BookProductRepository{

    Optional<BookProduct> findById(Long bookId);

}
