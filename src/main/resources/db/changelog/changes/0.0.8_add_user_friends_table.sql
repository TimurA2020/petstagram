CREATE TABLE IF NOT EXISTS users_friends(
    user_id INT REFERENCES users(id) NOT NULL,
    friends_id INT REFERENCES users(id) NOT NULL,
    PRIMARY KEY (user_id, friends_id)
)