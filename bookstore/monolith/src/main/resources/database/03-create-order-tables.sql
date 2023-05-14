--liquibase formatted sql
--changeset akej:1

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

--changeset akej:2
CREATE TABLE deliveries (
    delivery_id BIGINT AUTO_INCREMENT NOT NULL,
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