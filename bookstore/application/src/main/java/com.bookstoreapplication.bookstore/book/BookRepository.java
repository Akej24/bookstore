package com.bookstoreapplication.bookstore.book;

import com.bookstoreapplication.bookstore.purchase.order.OrderDetail;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;

import java.util.List;

interface BookRepository{

    Book findBookByBookId(long bookId);

    Page<Book> findAll(Specification<Book> specification, Pageable pageable);

    List<Book> findAll();

    <S extends Book> S save(S entity);

    <S extends Book> List<S> saveAll(Iterable<S> entities);

    void deleteBookByBookId(long BookId);

    void deleteAllBy();

    boolean existsByBookId(long bookId);

    boolean existsByBookTitle_BookTitleAndBookAuthor_BookAuthor(String title, String author);

}