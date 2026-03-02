CREATE TABLE customerRequest (
    id UUID PRIMARY KEY,
    first_name TEXT,
    last_name TEXT,
    email TEXT
);

-- ALTER TABLE customerRequest ADD CONSTRAINT uk_first_name_last_name_email UNIQUE (first_name, last_name, email);