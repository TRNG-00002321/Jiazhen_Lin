DROP TABLE IF EXISTS users;
DROP TABLE IF EXISTS expenses;
DROP TABLE IF EXISTS approvals;

CREATE TABLE users(
    id INTEGER PRIMARY KEY AUTOINCREMENT,
    username varchar(25) NOT NULL UNIQUE,
    password varchar(225) NOT NULL,
    role NOT NULL CHECK(role IN ('employee', 'manager'))
);

CREATE TABLE expenses(
    id INTEGER PRIMARY KEY AUTOINCREMENT,
    user_id INT NOT NULL,
    amount REAL NOT NULL,
    description VARCHAR(225),
    date varchar(255) NOT NULL,
    FOREIGN KEY(user_id) REFERENCES users(id) ON DELETE SET NULL
);

CREATE TABLE approvals(
    id INTEGER PRIMARY KEY AUTOINCREMENT,
    expense_id INT NOT NULL,
    status NOT NULL CHECK(status IN ('pending', 'approved', 'denied')),
    reviewer INT,
    comment VARCHAR(255),
    review_date VARCHAR(255),
    FOREIGN KEY(expense_id) REFERENCES expenses(id) ON DELETE CASCADE
);
