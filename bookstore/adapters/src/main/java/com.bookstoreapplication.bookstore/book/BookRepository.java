package com.bookstoreapplication.bookstore.book;

import com.bookstoreapplication.bookstore.book.BookQueryResponse;
import com.bookstoreapplication.bookstore.book.core.Book;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

@Repository
public interface BookRepository extends JpaRepository<Book, Long>, JpaSpecificationExecutor<Book> {

    Book findBookByBookId_BookId(long bookId);

    BookQueryResponse findBookDtoByBookId_BookId(long bookId);

    Page<Book> findAll(Specification<Book> specification, Pageable pageable);

    <S extends Book> S save(S entity);

    void deleteBookByBookId_BookId(long BookId);

    void deleteAllBy();
}