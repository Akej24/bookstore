--liquibase formatted sql
--changeset akej:1

CREATE TABLE payments (
    payment_id UUID NOT NULL,
    service_type VARCHAR(255) NOT NULL,
    service_id UUID NOT NULL,
    payment_method VARCHAR(255) NOT NULL,
    total_price NUMERIC(10,2) NOT NULL,
    payment_status VARCHAR(255) NOT NULL,
    PRIMARY KEY(payment_id),
    FOREIGN KEY(service_id) REFERENCES orders(order_id)
);