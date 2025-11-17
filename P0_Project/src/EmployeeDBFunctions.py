def username_exists(connection, username:str ) -> bool:
    with connection:
        cursor = connection.cursor()
        cursor.execute("""SELECT * FROM users WHERE username = ?""",(username,))
        if cursor.fetchone() is not None: return True
    return False

def user_id_exists(connection, user_id:int) -> bool:
    with connection:
        cursor = connection.cursor()
        cursor.execute("""SELECT * FROM users WHERE id = ?""", (user_id,))
        if cursor.fetchone() is not None: return True
    return False

def expense_id_exists(connection, expense_id:int) -> bool:
    with connection:
        cursor = connection.cursor()
        cursor.execute("""SELECT * FROM expenses WHERE id = ?""", (expense_id,))
        if cursor.fetchone() is not None: return True
    return False

def new_user(connection, username:str, encoded_password:str) -> None:
    with connection:
        cursor = connection.cursor()
        cursor.execute("""INSERT INTO users (username, password, role)
                          VALUES (?, ?, 'employee')""",
                          (username, encoded_password))

def log_in(connection, username:str, encoded_password:str) -> list:
    with connection:
        cursor = connection.cursor()
        cursor.execute("""SELECT id FROM users
                          WHERE username = ? AND password = ?""",
                          (username, encoded_password))
        result = cursor.fetchone()
        return result

def new_expense(connection, user_id:int, amount:float, description:str, date) -> int:
    with connection:
        cursor = connection.cursor()
        cursor.execute("""INSERT INTO expenses (user_id, amount, description, date)
                          VALUES (?, ?, ?, ?)""",
                          (user_id, amount, description, date))
        return cursor.lastrowid

def new_pending_approval(connection, expense_id:int) -> None:
    with connection:
        cursor = connection.cursor()
        cursor.execute("""INSERT INTO approvals (expense_id, status)
                          VALUES (?, "pending")""",(expense_id, ))


def view_submitted_expenses(connection, user_id:int) -> list:
    with connection:
        cursor = connection.cursor()
        cursor.execute("""SELECT * FROM expenses
                          WHERE user_id = ? """,(user_id,))
        result = cursor.fetchall()
        return result

def get_pending_expenses(connection, user_id:int)-> list:
    with connection:
        cursor = connection.cursor()
        cursor.execute("""SELECT * FROM expenses
                          INNER JOIN  approvals
                          ON expenses.id = approvals.expense_id
                          WHERE approvals.status = 'pending' AND expenses.user_id = ?""", (user_id,))
        result = cursor.fetchall()
        return result

def modify_expense(connection, user_id:int, expense_id:int, amount: float, description: str,
                   date) -> None:
    with connection:
        cursor = connection.cursor()
        cursor.execute("""UPDATE expenses
                          SET amount      = ?,
                              description = ?,
                              date        = ?
                          WHERE expenses.id = ?
                            AND expenses.user_id = ?
                            AND EXISTS (SELECT 1
                                        FROM approvals
                                        WHERE approvals.expense_id = ?
                                          AND approvals.status = 'pending')""",
                          (amount, description, date, expense_id, user_id, expense_id))

def delete_expense(connection, user_id: int, expense_id: int) ->None:
    with connection:
        cursor = connection.cursor()
        cursor.execute("""DELETE FROM expenses 
                          WHERE user_id = ? AND id = ?
                          AND EXISTS(SELECT 1 
                                    FROM approvals
                                    WHERE approvals.expense_id = ?)""",
                          (user_id, expense_id, expense_id))

def view_completed_expenses(connection, user_id:int) -> list:
    with connection:
        cursor = connection.cursor()
        cursor.execute("""SELECT expenses.* FROM expenses
                          INNER JOIN  approvals
                          ON expenses.id = approvals.expense_id 
                          WHERE approvals.status in ('approved', 'denied') AND user_id = ?""",
                          (user_id, ))
        result = cursor.fetchall()
        return result
