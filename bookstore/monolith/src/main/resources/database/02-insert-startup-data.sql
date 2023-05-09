--liquibase formatted sql
--changeset akej:1
INSERT INTO books (book_title, book_author, release_date, number_of_pages, availability_status, available_pieces, book_price, created_on)
VALUES ('Title1', 'Author1', '2023-02-02', 500, true, 10, 15.15, now());

INSERT INTO books (book_title, book_author, release_date, number_of_pages, availability_status, available_pieces, book_price, created_on)
VALUES ('Title2', 'Author2', '2023-02-02', 400, true, 15, 5.36, now());

INSERT INTO books (book_title, book_author, release_date, number_of_pages, availability_status, available_pieces, book_price, created_on)
VALUES ('Title1', 'Author1', '2023-02-02', 600, false, 0, 9.31, now());