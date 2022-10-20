CREATE TABLE posts(
    id SERIAL PRIMARY KEY NOT NULL,
    user_id INT NOT NULL,
    title TEXT NOT NULL,
    image TEXT,
    date TIMESTAMP without time zone,
    CONSTRAINT fk_users FOREIGN KEY(user_id) REFERENCES users(id)
)