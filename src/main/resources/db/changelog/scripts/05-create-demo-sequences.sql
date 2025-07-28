--liquibase formatted sql

--changeset nalemian:create-seq
CREATE SEQUENCE parent_seq START WITH 1 INCREMENT BY 50;
CREATE SEQUENCE child_seq START WITH 1 INCREMENT BY 50;