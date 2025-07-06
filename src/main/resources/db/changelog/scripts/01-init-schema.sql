--liquibase formatted sql

--changeset nalemian:init
CREATE TABLE users
(
    id        BIGSERIAL PRIMARY KEY,
    full_name VARCHAR(255) NOT NULL
);

CREATE TABLE accounts
(
    id             BIGSERIAL PRIMARY KEY,
    user_id        BIGINT REFERENCES users (id),
    account_number varchar NOT NULL,
    balance        DOUBLE PRECISION
);

CREATE TABLE book
(
    id           BIGSERIAL PRIMARY KEY,
    title        VARCHAR(255) NOT NULL,
    price        NUMERIC(10, 2),
    publish_date DATE
);
