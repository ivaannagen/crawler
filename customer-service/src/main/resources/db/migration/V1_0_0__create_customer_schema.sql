CREATE TABLE customer (
    id UUID PRIMARY KEY,
    first_name TEXT NOT NULL,
    last_name TEXT NOT NULL ,
    email TEXT NOT NULL,
    CONSTRAINT ck_customer_first_name_not_empty CHECK (TRIM(first_name) <> ''),
    CONSTRAINT ck_customer_last_name_not_empty CHECK (TRIM(last_name) <> ''),
    CONSTRAINT ck_customer_email_not_empty CHECK (TRIM(email) <> '')
);