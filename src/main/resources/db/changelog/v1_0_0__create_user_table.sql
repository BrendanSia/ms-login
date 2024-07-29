--liquibase formatted sql
--changeset author:brendan id:create-t_product

CREATE TABLE security.t_user (
    id SERIAL PRIMARY KEY,
    username VARCHAR(255) NOT NULL,
    password VARCHAR(255) NOT NULL,
    is_admin BOOLEAN NOT NULL DEFAULT FALSE
);

INSERT INTO t_user (username, password, is_admin) VALUES ('user1', 'userPass', FALSE);
INSERT INTO t_user (username, password, is_admin) VALUES ('admin1', 'your_encoded_password', TRUE);