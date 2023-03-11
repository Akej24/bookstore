--liquibase formatted sql
--changeset akej:1
INSERT INTO users (email, username, password, name, surname, date_of_birth, role, available_funds, purchased_books, locked, enabled)
VALUES ('admin@test.com', 'admin', 'Admin123!', 'admin', 'admin', '2023-01-01', 'ADMIN', 100.00, 0, false, true);
INSERT INTO users (email, username, password, name, surname, date_of_birth, role, available_funds, purchased_books, locked, enabled)
VALUES ('user@test.com', 'user', 'User123!', 'user', 'user', '2023-01-01', 'USER', 100.00, 0, false, true);

--changeset akej:2
INSERT INTO books (title, author, release_date, number_of_pages, status, available_pieces, price)
VALUES ('Title1', 'Author1', '2023-02-02', 500, true, 10, 25.00);

INSERT INTO books (title, author, release_date, number_of_pages, status, available_pieces, price)
VALUES ('Title2', 'Author2', '2023-02-02', 400, true, 15, 15.00);

INSERT INTO books (title ,author, release_date, number_of_pages, status, available_pieces, price)
VALUES ('Title1', 'Author1', '2023-02-02', 600, false, 0, 45.00);