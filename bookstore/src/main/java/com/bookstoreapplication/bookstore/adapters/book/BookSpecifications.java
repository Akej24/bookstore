package com.bookstoreapplication.bookstore.adapters.book;

import com.bookstoreapplication.bookstore.domain.book.core.Book;
import org.springframework.data.jpa.domain.Specification;

import java.time.LocalDate;

class BookSpecifications {
    static Specification<Book> hasTitleContainingIgnoreCase(String title) {
        return (root, query, criteriaBuilder) -> {
            if (title == null || title.isEmpty()) {
                return criteriaBuilder.conjunction();
            } else {
                return criteriaBuilder.like(
                        criteriaBuilder.lower(root.get("title")), "%" + title.toLowerCase() + "%");
            }
        };
    }

    static Specification<Book> hasAuthorContainingIgnoreCase(String author) {
        return (root, query, criteriaBuilder) -> {
            if (author == null || author.isEmpty()) {
                return criteriaBuilder.conjunction();
            } else {
                return criteriaBuilder.like(
                        criteriaBuilder.lower(root.get("author")), "%" + author.toLowerCase() + "%");
            }
        };
    }

    static Specification<Book> hasReleaseDateAfter(LocalDate releaseDate) {
        return (root, query, criteriaBuilder) -> {
            if (releaseDate == null) {
                return criteriaBuilder.conjunction();
            } else {
                return criteriaBuilder.greaterThanOrEqualTo(
                        root.get("releaseDate"), releaseDate);
            }
        };
    }

    static Specification<Book> hasNumberOfPagesGreaterThanOrEqual(Integer numberOfPages) {
        return (root, query, criteriaBuilder) -> {
            if (numberOfPages == null || numberOfPages < 0) {
                return criteriaBuilder.conjunction();
            } else {
                return criteriaBuilder.greaterThanOrEqualTo(
                        root.get("numberOfPages"), numberOfPages);
            }
        };
    }

    static Specification<Book> hasStatus(Boolean status) {
        return (root, query, criteriaBuilder) -> {
            if (status == null) {
                return criteriaBuilder.conjunction();
            } else {
                return criteriaBuilder.equal(root.get("status"), status);
            }
        };
    }

    static Specification<Book> hasAvailablePiecesContainingIgnoreCase(String availablePieces) {
        return (root, query, criteriaBuilder) -> {
            if (availablePieces == null || availablePieces.isEmpty()) {
                return criteriaBuilder.conjunction();
            } else {
                return criteriaBuilder.like(
                        criteriaBuilder.lower(root.get("availablePieces")), "%" + availablePieces.toLowerCase() + "%");
            }
        };
    }

    static Specification<Book> hasPriceContainingIgnoreCase(String price) {
        return (root, query, criteriaBuilder) -> {
            if (price == null || price.isEmpty()) {
                return criteriaBuilder.conjunction();
            } else {
                return criteriaBuilder.like(
                        criteriaBuilder.lower(root.get("price")), "%" + price.toLowerCase() + "%");
            }
        };
    }
}
