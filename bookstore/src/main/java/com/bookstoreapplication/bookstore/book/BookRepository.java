package com.bookstoreapplication.bookstore.book;

import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;


@Repository
public interface BookRepository extends JpaRepository<Book, Long> {

    @Query("select book from Book book")
    List<Book> findAllBooks(Pageable page);

}