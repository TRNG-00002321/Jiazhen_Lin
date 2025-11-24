SET FOREIGN_KEY_CHECKS = 0;
DROP TABLE IF EXISTS users;
DROP TABLE IF EXISTS expenses;
DROP TABLE IF EXISTS approvals;
SET FOREIGN_KEY_CHECKS = 1;

CREATE TABLE users(
    id INTEGER PRIMARY KEY AUTO_INCREMENT,
    username varchar(25) NOT NULL UNIQUE,
    password varchar(225) NOT NULL,
    role ENUM('employee', 'manager') NOT NULL
);

CREATE TABLE expenses(
    id INTEGER PRIMARY KEY AUTO_INCREMENT,
    user_id INT,
    amount REAL NOT NULL,
    description VARCHAR(225),
    date varchar(255) NOT NULL,
    FOREIGN KEY(user_id) REFERENCES users(id) ON DELETE SET NULL
);

CREATE TABLE approvals(
    id INTEGER PRIMARY KEY AUTO_INCREMENT,
    expense_id INT NOT NULL,
    status ENUM('pending', 'approved', 'denied') NOT NULL,
    reviewer INT,
    comment VARCHAR(255),
    review_date VARCHAR(255),
    FOREIGN KEY(expense_id) REFERENCES expenses(id) ON DELETE CASCADE
);
