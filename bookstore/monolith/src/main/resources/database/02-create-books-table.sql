--liquibase formatted sql
--changeset akej:1

CREATE TABLE books (
    book_id BIGINT AUTO_INCREMENT NOT NULL,
    book_title VARCHAR(255) NOT NULL,
    book_author VARCHAR(255) NOT NULL,
    release_date DATE NOT NULL,
    number_of_pages INT NOT NULL,
    availability_status BOOLEAN NOT NULL,
    available_pieces INT NOT NULL,
    book_price NUMERIC(10,2) NOT NULL,
    created_on DATETIME,
    updated_on DATETIME,
    PRIMARY KEY(book_id)
);