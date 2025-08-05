CREATE TABLE IF NOT EXISTS "donor"(
    id SERIAL PRIMARY KEY,
    full_name VARCHAR(255) NOT NULL,
    email VARCHAR(255) NOT NULL
);

CREATE TABLE IF NOT EXISTS "payment"(
    id SERIAL PRIMARY KEY,
    method VARCHAR(255) NOT NULL,
    amount BIGINT NOT NULL,
    created_at TIMESTAMP NOT NULL,
    status TEXT NOT NULL
);

CREATE TABLE IF NOT EXISTS "donation"(
    id SERIAL PRIMARY KEY,
    donor_id BIGINT NOT NULL REFERENCES "donor"(id),
    payment_id BIGINT NOT NULL REFERENCES "payment"(id)
);

CREATE TABLE IF NOT EXISTS "beneficiary"(
    id SERIAL PRIMARY KEY,
    full_name VARCHAR(255) NOT NULL,
    email VARCHAR(255) NOT NULL
);

CREATE TABLE IF NOT EXISTS "help"(
    id SERIAL PRIMARY KEY,
    beneficiary_id BIGINT NOT NULL REFERENCES "beneficiary"(id),
    payment_id BIGINT NOT NULL REFERENCES "payment"(id),
    description TEXT
);