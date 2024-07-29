--liquibase formatted sql
--changeset author:brendan id:create-t_user

CREATE TABLE security.t_user (
    id SERIAL PRIMARY KEY,
    username VARCHAR(255) NOT NULL,
    password VARCHAR(255) NOT NULL,
    email VARCHAR(255),
    roles TEXT[],
    is_admin BOOLEAN NOT NULL DEFAULT FALSE
);

INSERT INTO t_user (username, password, email, roles, is_admin) VALUES ('user1', '$2a$12$./anMZMis8f8iUwVTGICq.X4HzLMS4vC9CSj7BGSBkugvGJdW4lNW', 'user@email.com', '{"EMPLOYEE"}', FALSE);
INSERT INTO t_user (username, password, email, roles, is_admin) VALUES ('admin1', '$2a$12$9GYtMqB5OI.y9myNSfUSGObJ/DVX.80Vs97NPqRT2f1kTBdVVKvkm', 'admin@email.com', '{"EMPLOYEE", "ADMIN"}', TRUE);