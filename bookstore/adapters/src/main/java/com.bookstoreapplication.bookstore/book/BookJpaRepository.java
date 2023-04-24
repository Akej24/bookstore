package com.bookstoreapplication.bookstore.book;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

@Repository
interface BookJpaRepository extends
        BookRepository,
        JpaRepository<Book, Long>,
        JpaSpecificationExecutor<Book> {

}