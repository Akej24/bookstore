--liquibase formatted sql
--changeset akej:1
CREATE TABLE users (
    user_id BIGINT AUTO_INCREMENT,
    email VARCHAR(255) NOT NULL,
    username VARCHAR(255) NOT NULL,
    encoded_password VARCHAR(255) NOT NULL,
    first_name VARCHAR(255) NOT NULL,
    last_name VARCHAR(255) NOT NULL,
    date_of_birth DATE NOT NULL,
    role VARCHAR(255) NOT NULL,
    funds DECIMAL,
    created_on DATETIME,
    updated_on DATETIME,
    last_login DATETIME,
    locked BOOLEAN,
    enabled BOOLEAN,
    PRIMARY KEY(user_id)
);

--changeset akej:2
CREATE TABLE books (
    book_id BIGINT AUTO_INCREMENT,
    book_title VARCHAR(255) NOT NULL,
    book_author VARCHAR(255) NOT NULL,
    release_date DATE NOT NULL,
    number_of_pages INT NOT NULL,
    availability_status BOOLEAN NOT NULL,
    available_pieces INT NOT NULL,
    book_price DECIMAL NOT NULL,
    created_on DATETIME,
    updated_on DATETIME,
    PRIMARY KEY(book_id)
);

--changeset akej:3
CREATE TABLE orders (
    order_id UUID NOT NULL,
    customer_id BIGINT NOT NULL,
    payment_method VARCHAR(255) NOT NULL,
    order_date DATETIME NOT NULL,
    order_status VARCHAR(255) NOT NULL,
    PRIMARY KEY(order_id)
);

CREATE TABLE order_details (
    order_detail_id BIGINT AUTO_INCREMENT,
    order_id UUID NOT NULL,
    book_id BIGINT NOT NULL,
    book_title VARCHAR(255) NOT NULL,
    book_author VARCHAR(255) NOT NULL,
    book_price DECIMAL(10, 2) NOT NULL,
    books_amount INT NOT NULL,
    PRIMARY KEY(order_detail_id),
    FOREIGN KEY (order_id) REFERENCES orders (order_id)
);

--changeset akej:4
CREATE TABLE delivery (
    delivery_id BIGINT AUTO_INCREMENT,
    order_id UUID NOT NULL,
    order_number BINARY(16) NOT NULL,
    street VARCHAR(255) NOT NULL,
    street_number INTEGER NOT NULL,
    zip_code VARCHAR(20) NOT NULL,
    city VARCHAR(255) NOT NULL,
    first_name VARCHAR(255) NOT NULL,
    last_name VARCHAR(255) NOT NULL,
    phone_number VARCHAR(20) NOT NULL,
    delivery_status VARCHAR(255) NOT NULL,
    placing_order_date DATETIME,
    send_date DATETIME,
    receive_date DATETIME,
    PRIMARY KEY(delivery_id),
    FOREIGN KEY(order_id) REFERENCES orders(order_id)
);