--liquibase formatted sql
--changeset akej:1
CREATE TABLE users (
    user_id BIGINT PRIMARY KEY AUTO_INCREMENT,
    email VARCHAR(255) NOT NULL,
    username VARCHAR(255) NOT NULL,
    password VARCHAR(255) NOT NULL,
    name VARCHAR(255) NOT NULL,
    surname VARCHAR(255) NOT NULL,
    date_of_birth DATE NOT NULL,
    role VARCHAR(255) NOT NULL,
    available_funds DOUBLE,
    purchased_books INT,
    created_on DATETIME,
    updated_on DATETIME,
    last_login DATETIME,
    locked BOOLEAN,
    enabled BOOLEAN
);

CREATE TABLE books (
    book_id BIGINT PRIMARY KEY AUTO_INCREMENT,
    title VARCHAR(255) NOT NULL,
    author VARCHAR(255) NOT NULL,
    release_date DATE NOT NULL,
    number_of_pages INT NOT NULL,
    status BOOLEAN NOT NULL,
    available_pieces INT NOT NULL,
    price DOUBLE NOT NULL,
    created_on DATETIME,
    updated_on DATETIME
);
--changeset akej:2
CREATE TABLE purchases (
    purchase_id BIGINT PRIMARY KEY AUTO_INCREMENT,
    user_id BIGINT NOT NULL,
    purchase_date DATETIME NOT NULL,
    total_price DOUBLE,
    purchase_status VARCHAR(255) NOT NULL,
    FOREIGN KEY (user_id) REFERENCES users(user_id)
);

CREATE TABLE purchase_details (
    purchase_detail_id BIGINT PRIMARY KEY AUTO_INCREMENT,
    book_id BIGINT NOT NULL,
    books_amount INT NOT NULL,
    purchase_id BIGINT NOT NULL,
    FOREIGN KEY (book_id) REFERENCES books (book_id),
    FOREIGN KEY (purchase_id) REFERENCES purchases (purchase_id)
);