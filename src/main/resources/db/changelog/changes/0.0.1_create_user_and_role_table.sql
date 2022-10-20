CREATE TABLE users(
    id SERIAL PRIMARY KEY NOT NULL,
    first_name TEXT NOT NULL,
    last_name TEXT NOT NULL,
    email TEXT NOT NULL,
    password TEXT NOT NULL
);

CREATE TABLE roles(
    id SERIAL PRIMARY KEY NOT NULL,
    role TEXT NOT NULL
);