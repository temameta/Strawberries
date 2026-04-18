CREATE UNIQUE INDEX IF NOT EXISTS unique_active_email
    ON users (email)
    WHERE active = true;

CREATE UNIQUE INDEX IF NOT EXISTS unique_active_phone
    ON users (phone)
    WHERE active = true;