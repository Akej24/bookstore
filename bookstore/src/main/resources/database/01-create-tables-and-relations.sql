--liquibase formatted sql
--changeset aleks:1
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
    purchase_history LONGTEXT,
    created_on DATETIME,
    updated_on DATETIME,
    last_login DATETIME,
    locked BOOLEAN,
    enabled BOOLEAN
);

CREATE TABLE purchases (
    purchase_id BIGINT PRIMARY KEY AUTO_INCREMENT,

    purchase_date DATETIME NOT NULL,
    total_price DOUBLE,
    purchase_status VARCHAR(255) NOT NULL
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

--changeset aleks:2
ALTER TABLE users ADD COLUMN purchase_id BIGINT REFERENCES purchases(purchase_id);

ALTER TABLE purchases ADD COLUMN user_id BIGINT REFERENCES users(user_id);

CREATE TABLE purchases_details (
    purchase_book_id BIGINT PRIMARY KEY AUTO_INCREMENT,
    purchase_id BIGINT REFERENCES purchases(purchase_id),
    book_id BIGINT REFERENCES books(book_id),

    books_amount INT
);
