def username_exists(connection, username:str ) -> bool:
    
    cursor = connection.cursor()
    cursor.execute("""SELECT * FROM users WHERE username = %s""",(username,))
    if cursor.fetchone() is not None: return True
    return False

def user_id_exists(connection, user_id:int) -> bool:
    
    cursor = connection.cursor()
    cursor.execute("""SELECT * FROM users WHERE id = %s""", (user_id,))
    if cursor.fetchone() is not None: return True
    return False

def expense_id_exists(connection, expense_id:int) -> bool:
    
    cursor = connection.cursor()
    cursor.execute("""SELECT * FROM expenses WHERE id = %s""", (expense_id,))
    if cursor.fetchone() is not None: return True
    return False

def add_user(connection, username:str, encoded_password:str) -> None:

    
    cursor = connection.cursor()
    cursor.execute("""INSERT INTO users (username, password, role)
                      VALUES (%s, %s, 'employee')""",
                      (username, encoded_password))


def log_in(connection, username: str) -> list:
    cursor = connection.cursor()
    cursor.execute("""SELECT id, password
                      FROM users
                      WHERE username = %s""",
                   (username,))
    result = cursor.fetchone()
    return result

def add_expense(connection, user_id:int, amount:float, description:str, date) -> int:
    
        cursor = connection.cursor()
        cursor.execute("""INSERT INTO expenses (user_id, amount, description, date)
                          VALUES (%s, %s, %s, %s)""",
                          (user_id, amount, description, date))
        return cursor.lastrowid

def new_pending_approval(connection, expense_id:int) -> None:
    
        cursor = connection.cursor()
        cursor.execute("""INSERT INTO approvals (expense_id, status)
                          VALUES (%s, 'pending')""",(expense_id, ))

def view_all_expenses(connection, user_id:int) -> list:
    
        cursor = connection.cursor()
        cursor.execute("""SELECT * FROM expenses
                          WHERE user_id = %s """,(user_id,))
        result = cursor.fetchall()
        return result

def get_pending_expenses(connection, user_id:int)-> list:
    
        cursor = connection.cursor()
        cursor.execute("""SELECT expenses.*, approvals.status, approvals.comment FROM expenses
                          INNER JOIN  approvals
                          ON expenses.id = approvals.expense_id
                          WHERE approvals.status = 'pending' AND expenses.user_id = %s""", (user_id,))
        result = cursor.fetchall()
        return result

def modify_expense(connection, user_id:int, expense_id:int, amount: float, description: str,
                   date) -> None:
    
        cursor = connection.cursor()
        cursor.execute("""UPDATE expenses
                          SET amount      = %s,
                              description = %s,
                              date        = %s
                          WHERE expenses.id = %s
                            AND expenses.user_id = %s
                            AND EXISTS (SELECT 1
                                        FROM approvals
                                        WHERE approvals.expense_id = %s
                                          AND approvals.status = 'pending')""",
                          (amount, description, date, expense_id, user_id, expense_id))

def delete_expense(connection, user_id: int, expense_id: int) ->None:
    
        cursor = connection.cursor()
        cursor.execute("""DELETE FROM expenses 
                          WHERE user_id = %s AND id = %s""",
                          (user_id, expense_id))
        return cursor.lastrowid

def delete_pending_approval(connection, expense_id: int) -> None:
    
        cursor = connection.cursor()
        cursor.execute("""DELETE FROM approvals 
                          WHERE expense_id = %s""",
                          (expense_id,))

def view_completed_expenses(connection, user_id:int) -> list:
    
        cursor = connection.cursor()
        cursor.execute("""SELECT expenses.*, approvals.status, approvals.comment FROM expenses
                          INNER JOIN  approvals
                          ON expenses.id = approvals.expense_id 
                          WHERE approvals.status in ('approved', 'denied') AND user_id = %s""",
                          (user_id, ))
        result = cursor.fetchall()

        return result

def view_expense(connection, expense_id: int) -> list:
    
        cursor = connection.cursor()
        cursor.execute("""SELECT * FROM 
                          expenses
                          WHERE expenses.id = %s""", (expense_id,))
        result = cursor.fetchall()
        return result