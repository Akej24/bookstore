package com.bookstoreapplication.bookstore.book;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;

interface BookRepository{

    Book findBookByBookId(long bookId);

    Page<Book> findAll(Specification<Book> specification, Pageable pageable);

    <S extends Book> S save(S entity);

    void deleteBookByBookId(long BookId);

    void deleteAllBy();

    boolean existsByBookId(long bookId);

    boolean existsByTitle_TitleAndAuthor_Author(String title, String author);

}