CREATE TABLE customers
(
    id            SERIAL PRIMARY KEY,
    name          VARCHAR(255) NOT NULL,
    username      VARCHAR(255) NOT NULL UNIQUE,
    password      VARCHAR(255) NOT NULL,
    address       VARCHAR(255) NOT NULL,
    date_of_birth DATE         NOT NULL,
    id_document   VARCHAR(255) NOT NULL
);
CREATE TABLE accounts
(
    account_id   SERIAL PRIMARY KEY,
    customer_id  INTEGER        NOT NULL,
    iban         VARCHAR(22)    NOT NULL UNIQUE,
    account_type VARCHAR(100),
    balance      DECIMAL(15, 2) NOT NULL DEFAULT 0.00,
    currency     VARCHAR(3),
    CONSTRAINT fk_customer FOREIGN KEY (customer_id) REFERENCES customers (id) ON DELETE CASCADE
);
