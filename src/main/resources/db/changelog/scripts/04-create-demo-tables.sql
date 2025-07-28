CREATE TABLE parent
(
    id   BIGINT PRIMARY KEY,
    name VARCHAR(255) NOT NULL
);

CREATE TABLE child
(
    id        BIGINT PRIMARY KEY,
    parent_id BIGINT       NOT NULL,
    name      VARCHAR(255) NOT NULL,
    CONSTRAINT fk_child_parent FOREIGN KEY (parent_id) REFERENCES parent (id)
);