import datetime
import hashlib
try:
    import EmployeeDBFunctions
except ImportError:
    from . import EmployeeDBFunctions

def add_user(connection, username: str, password: str) -> None:
    if not username or not password:
        raise ValueError("Username and password cannot be empty")
    if len(username) > 25:
        raise ValueError("Username cannot be longer than 25 characters")

    if EmployeeDBFunctions.username_exists(connection, username):
        raise ValueError("Username already exists")

    encoded_password = hashlib.sha256(username.encode() + password.encode()).hexdigest()

    EmployeeDBFunctions.add_user(connection, username, encoded_password)


def log_in(connection, username:str, password:str) -> int:
    if not(username and password):
        raise ValueError("username and password cannot be empty")
    encoded_password = hashlib.sha256(username.encode() + password.encode()).hexdigest()
    result = EmployeeDBFunctions.log_in(connection, username, encoded_password)
    if result is None:
        raise ValueError("username and/or password is invalid")
    return result[0]


def valid_amount(amount: float) -> bool:
    temp = str(amount).split(".")
    if len(temp) > 1:
        if len(temp[1]) > 2:
            return False
    return True if amount>0 else False

def add_expense(connection, user_id: int, amount: float, description: str = "",
                date = datetime.date.today()) -> int:
    if not valid_amount(amount):
        raise ValueError("Must provide valid amount")

    if EmployeeDBFunctions.user_id_exists(connection, user_id):
        expense_id = EmployeeDBFunctions.add_expense(connection, user_id, amount, description, date)
        EmployeeDBFunctions.new_pending_approval(connection, expense_id)
        return expense_id
    else:
        raise ValueError("Invalid user")


def view_submitted_expenses(connection, user_id: int) -> list:
    if EmployeeDBFunctions.user_id_exists(connection, user_id):
        result = EmployeeDBFunctions.view_submitted_expenses(connection, user_id)
        return result if result != [] else None
    else:
        raise ValueError("Invalid user")

def get_pending_expenses(connection, user_id: int) -> list:
    if EmployeeDBFunctions.user_id_exists(connection, user_id):
        result = EmployeeDBFunctions.get_pending_expenses(connection, user_id)
        return result if result != [] else None
    else:
        raise ValueError("Invalid user")

def is_pending_approval(connection, user_id: int, expense_id: int) -> bool:
    if EmployeeDBFunctions.expense_id_exists(connection, expense_id):
        pending = get_pending_expenses(connection, user_id)
        if pending is None:
            return False

        pending_ids = [expense[0] for expense in pending]
        if expense_id not in pending_ids:
            return False
    return True

def modify_expense(connection, user_id: int, expense_id: int, amount: float, description: str="",
                   date=datetime.date.today()) -> None:
    if EmployeeDBFunctions.expense_id_exists(connection, expense_id):
        if is_pending_approval(connection, user_id, expense_id):
            EmployeeDBFunctions.modify_expense(connection, user_id, expense_id, amount, description, date)

        else:
            raise ValueError("Invalid expense")
    else:
        raise ValueError("Invalid expense")

def delete_expense(connection, user_id: int, expense_id: int) -> None:
    if EmployeeDBFunctions.expense_id_exists(connection, expense_id):
        if is_pending_approval(connection, user_id, expense_id):
            EmployeeDBFunctions.delete_expense(connection, user_id, expense_id)
            EmployeeDBFunctions.delete_pending_approval(connection, expense_id)
        else:
            raise ValueError("Invalid expense")
    else:
        raise ValueError("Invalid expense")

def view_completed_expenses(connection, user_id: int) -> list:
    if EmployeeDBFunctions.user_id_exists(connection, user_id):
        result = EmployeeDBFunctions.view_completed_expenses(connection, user_id)
        return result if result != [] else None
    else:
        raise ValueError("Invalid user")

def view_expense(connection, expense_id: int) -> list:
    if EmployeeDBFunctions.expense_id_exists(connection, expense_id):
        result = EmployeeDBFunctions.view_expense(connection, expense_id)
        return result if result != [] else None
    else:
        raise ValueError("Invalid expense")