package com.bookstoreapplication.bookstore.book;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;

interface BookRepository{

    Book findBookByBookId_BookId(long bookId);

    BookQueryResponse findBookDtoByBookId_BookId(long bookId);

    Page<Book> findAll(Specification<Book> specification, Pageable pageable);

    <S extends Book> S save(S entity);

    void deleteBookByBookId_BookId(long BookId);

    void deleteAllBy();

    boolean existsById(long bookId);
}