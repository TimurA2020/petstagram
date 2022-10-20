CREATE TABLE friendrequests(
    id SERIAL PRIMARY KEY NOT NULL,
    sender_id INT NOT NULL,
    receiver_id INT NOT NULL,
    date TIMESTAMP WITHOUT TIME ZONE,
    CONSTRAINT sender_id_fk FOREIGN KEY(sender_id) REFERENCES users(id),
    CONSTRAINT receiver_id_fk FOREIGN KEY(receiver_id) REFERENCES users(id)
)