package com.bookstoreapplication.bookstore.book;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

@Repository
interface BookRepository extends JpaRepository<Book, Long>, JpaSpecificationExecutor<Book> {

    Book findBookByBookId_BookId(long bookId);

    BookDto findBookDtoByBookId_BookId(long bookId);

    Page<Book> findAll(Specification<Book> specification, Pageable pageable);

    <S extends Book> S save(S entity);

    void deleteBookByBookId_BookId(long BookId);

    void deleteAllBy();
}