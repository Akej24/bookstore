--liquibase formatted sql
--changeset akej:1

CREATE TABLE users (
    user_id BIGINT AUTO_INCREMENT NOT NULL,
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
    locked BOOLEAN NOT NULL,
    enabled BOOLEAN NOT NULL,
    PRIMARY KEY(user_id)
);
