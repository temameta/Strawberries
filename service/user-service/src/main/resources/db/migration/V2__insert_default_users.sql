-- Пароли: BCrypt-хэш строки "password"
INSERT INTO users (id, first_name, email, phone, password_hash, role, active, created_at)
VALUES
    (gen_random_uuid(), 'Admin',   'admin@strawberries.org', '+70000000001',
     '$2a$12$...adminHashHere...', 'ADMIN', TRUE, now()),
    (gen_random_uuid(), 'Regular', 'user@strawberries.org',  '+70000000002',
     '$2a$12$...userHashHere...',  'USER',  TRUE, now())
ON CONFLICT DO NOTHING;