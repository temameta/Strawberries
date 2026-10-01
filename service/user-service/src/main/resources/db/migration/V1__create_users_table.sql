CREATE TABLE IF NOT EXISTS users (
                                     id            UUID          NOT NULL DEFAULT gen_random_uuid() PRIMARY KEY,
                                     first_name    VARCHAR(255)  NOT NULL,
                                     email         VARCHAR(255),
                                     phone         VARCHAR(50)   NOT NULL,
                                     password_hash VARCHAR(255)  NOT NULL,
                                     role          VARCHAR(50)   NOT NULL CHECK (role IN ('ADMIN', 'USER')),
                                     active        BOOLEAN       NOT NULL DEFAULT TRUE,
                                     created_at    TIMESTAMP     NOT NULL DEFAULT now(),
                                     updated_at    TIMESTAMP
);

CREATE UNIQUE INDEX IF NOT EXISTS unique_active_email
    ON users (email)
    WHERE active = true;

CREATE UNIQUE INDEX IF NOT EXISTS unique_active_phone
    ON users (phone)
    WHERE active = true;