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
