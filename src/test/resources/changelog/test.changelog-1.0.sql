--liquibase formatted sql

--changeset yuriy_nekludov:1
CREATE TABLE users
(
    id BIGINT PRIMARY KEY AUTO_INCREMENT,
    username VARCHAR(25) NOT NULL UNIQUE CHECK ( LENGTH(username) > 3 ),
    password VARCHAR(255) NOT NULL CHECK ( LENGTH(password) > 2 ),
    role ENUM('USER', 'ADMIN') NOT NULL
);