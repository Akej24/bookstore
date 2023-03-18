--liquibase formatted sql
--changeset akej:1
INSERT INTO users (email, username, password, name, surname, date_of_birth, role, available_funds, locked, enabled, created_on)
VALUES ('admin@test.com', 'admin', 'Admin123!', 'admin', 'admin', '2023-01-01', 'ADMIN', 1000.00, false, true, now());
INSERT INTO users (email, username, password, name, surname, date_of_birth, role, available_funds, locked, enabled, created_on)
VALUES ('user@test.com', 'user', 'User123!', 'user', 'user', '2023-01-01', 'USER', 1000.00, false, true, now());

--changeset akej:2
INSERT INTO books (title, author, release_date, number_of_pages, status, available_pieces, price, created_on)
VALUES ('Title1', 'Author1', '2023-02-02', 500, true, 10, 15.15, now());

INSERT INTO books (title, author, release_date, number_of_pages, status, available_pieces, price, created_on)
VALUES ('Title2', 'Author2', '2023-02-02', 400, true, 15, 5.36, now());

INSERT INTO books (title ,author, release_date, number_of_pages, status, available_pieces, price, created_on)
VALUES ('Title1', 'Author1', '2023-02-02', 600, false, 0, 9.31, now());