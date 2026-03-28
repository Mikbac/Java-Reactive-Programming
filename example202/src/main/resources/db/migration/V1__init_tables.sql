CREATE TABLE IF NOT EXISTS users
(
    id
    serial
    PRIMARY
    KEY,
    username
    TEXT,
    email
    TEXT
);

INSERT INTO users (username, email)
VALUES
    ('john_smith', 'john.smith@example.com'),
    ('emma_johnson', 'emma.johnson@example.com'),
    ('liam_williams', 'liam.williams@example.com'),
    ('olivia_brown', 'olivia.brown@example.com'),
    ('noah_jones', 'noah.jones@example.com'),
    ('ava_garcia', 'ava.garcia@example.com'),
    ('william_miller', 'william.miller@example.com'),
    ('sophia_davis', 'sophia.davis@example.com'),
    ('james_rodriguez', 'james.rodriguez@example.com'),
    ('isabella_martinez', 'isabella.martinez@example.com');
