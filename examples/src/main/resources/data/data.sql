DROP TABLE IF EXISTS employee;
DROP TABLE IF EXISTS department;

CREATE TABLE department
(
    id   INT AUTO_INCREMENT PRIMARY KEY,
    name VARCHAR(100) UNIQUE NOT NULL
);

CREATE TABLE employee
(
    id            INT AUTO_INCREMENT PRIMARY KEY,
    name          VARCHAR(50)         NOT NULL,
    email         VARCHAR(100) UNIQUE NOT NULL,
    department_id INT,
    created_at    TIMESTAMP WITH TIME ZONE default CURRENT_TIMESTAMP,
    foreign key (department_id) references department (id)
);

INSERT INTO department (name)
VALUES
    ('Engineering'),
    ('Human Resources'),
    ('Marketing');

INSERT INTO employee (name, email, department_id)
VALUES
    ('bob', 'bob@mail.com', 1),
    ('alice', 'alice@mail.com', 2),
    ('john', 'john@mail.com', 1),
    ('test', 'test@mail.com', 3),
    ('tets2', 'tets2@email.com', 2),
    ('tets3', 'tets3@email.com', 2),
    ('tets4', 'tets4@email.com', 2),
    ('tets5', 'tets5@email.com', 2);


