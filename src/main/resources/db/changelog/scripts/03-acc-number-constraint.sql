--liquibase formatted sql

--changeset kskuznet:acc-number-constraint

alter table accounts
add constraint ACCNUM check ( account_number ~ $pattern$^[A-z0-9]+$$pattern$ )