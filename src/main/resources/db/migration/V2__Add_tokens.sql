CREATE TABLE tokens (
                        id SERIAL PRIMARY KEY,
                        customer_id INTEGER NOT NULL,
                        token VARCHAR(255) NOT NULL UNIQUE,
                        expiration_date TIMESTAMP NOT NULL,
                        CONSTRAINT fk_customer FOREIGN KEY (customer_id) REFERENCES customers (id) ON DELETE CASCADE
);