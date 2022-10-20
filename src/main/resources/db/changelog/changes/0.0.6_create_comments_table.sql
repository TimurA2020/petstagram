CREATE TABLE comments(
    id SERIAL PRIMARY KEY NOT NULL,
    comment TEXT NOT NULL,
    date TIMESTAMP WITHOUT TIME ZONE,
    user_id INT NOT NULL,
    post_id INT NOT NULL,
    CONSTRAINT fk_users FOREIGN KEY(user_id) REFERENCES users(id),
    CONSTRAINT fk_posts FOREIGN KEY(post_id) REFERENCES posts(id)
)