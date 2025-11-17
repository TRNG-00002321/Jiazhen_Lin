import datetime
import hashlib
from . import EmployeeDBFunctions

class EmployeeFunctions:
    @staticmethod
    def add_user(connection, username: str, password: str) -> None:
        if not username or not password:
            raise ValueError("Username and password cannot be empty")
        if len(username) > 25:
            raise ValueError("Username cannot be longer than 25 characters")

        if EmployeeDBFunctions.username_exists(connection, username):
            raise ValueError("Username already exists")

        encoded_password = hashlib.sha256(username.encode() + password.encode()).hexdigest()

        EmployeeDBFunctions.new_user(connection, username, encoded_password)

    @staticmethod
    def log_in(connection, username:str, password:str) -> int:
        if not(username and password):
            raise ValueError("username and password cannot be empty")
        encoded_password = hashlib.sha256(username.encode() + password.encode()).hexdigest()
        result = EmployeeDBFunctions.log_in(connection, username, encoded_password)
        if result is None:
            raise ValueError("username and/or password is invalid")
        return result[0]

    @staticmethod
    def valid_amount(amount: float) -> bool:
        temp = str(amount).split(".")
        if len(temp) > 1:
            if len(temp[1]) > 2:
                return False
        return True if amount is not None else False

    @staticmethod
    def add_expense(connection, user_id: int, amount: float, description: str = "",
                    date = datetime.date.today()) -> int:
        if not EmployeeFunctions.valid_amount(amount):
            print(amount)
            raise ValueError("Must provide valid amount")

        if EmployeeDBFunctions.user_id_exists(connection, user_id):
            expense_id = EmployeeDBFunctions.new_expense(connection, user_id, amount, description, date)
            EmployeeDBFunctions.new_pending_approval(connection, expense_id)
            return expense_id
        else:
            raise ValueError("Invalid user")

    @staticmethod
    def view_submitted_expenses(connection, user_id: int) -> list:
        if EmployeeDBFunctions.user_id_exists(connection, user_id):
            result = EmployeeDBFunctions.view_submitted_expenses(connection, user_id)
            return result if result != [] else None
        else:
            raise ValueError("Invalid user")

    @staticmethod
    def get_pending_expenses(connection, user_id: int) -> list:
        if EmployeeDBFunctions.user_id_exists(connection, user_id):
            result = EmployeeDBFunctions.get_pending_expenses(connection, user_id)
            return result if result != [] else None
        else:
            raise ValueError("Invalid user")

    @staticmethod
    def is_pending_approval(connection, user_id: int, expense_id: int) -> bool:
        if EmployeeDBFunctions.expense_id_exists(connection, expense_id):
            pending = EmployeeFunctions.get_pending_expenses(connection, user_id)
            if pending is None:
                return False

            pending_ids = [expense[0] for expense in pending]
            if expense_id not in pending_ids:
                return False
        return True

    @staticmethod
    def modify_expense(connection, user_id: int, expense_id: int, amount: float, description: str="",
                       date=datetime.date.today()) -> None:
        if EmployeeDBFunctions.expense_id_exists(connection, expense_id):
            if EmployeeFunctions.is_pending_approval(connection, user_id, expense_id):
                EmployeeDBFunctions.modify_expense(connection, user_id, expense_id, amount, description, date)
            else:
                raise ValueError("Invalid expense")
        else:
            raise ValueError("Invalid expense")

    @staticmethod
    def delete_expense(connection, user_id: int, expense_id: int) -> None:
        if EmployeeDBFunctions.expense_id_exists(connection, expense_id):
            if EmployeeFunctions.is_pending_approval(connection, user_id, expense_id):
                EmployeeDBFunctions.delete_expense(connection, user_id, expense_id)
            else:
                raise ValueError("Invalid expense")
        else:
            raise ValueError("Invalid expense")

    @staticmethod
    def view_completed_expenses(connection, user_id: int) -> list:
        if EmployeeDBFunctions.user_id_exists(connection, user_id):
            result = EmployeeDBFunctions.view_completed_expenses(connection, user_id)
            return result if result != [] else None
        else:
            raise ValueError("Invalid user")
