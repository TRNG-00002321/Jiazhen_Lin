import pandas as pd
import Employee_Functions
from ExpenseCategories import Category

from ProjectWithMysql.src.BuildDB import build_employee_db, connect_employee_db

def start(conn):
    print("1. Create Employee User")
    print("2. Employee Login")
    print("3. Exit")
    option = input()
    try:
        match option:
            case '1':
                print("Create Employee User: ")
                username = input("Enter Username: ")
                password = input("Enter password: ")
                Employee_Functions.add_user(conn, username, password)
                return Employee_Functions.log_in(conn, username, password)
            case '2':
                print("Employee Login: ")
                username = input("Enter Username: ")
                password = input("Enter Password: ")
                return Employee_Functions.log_in(conn, username, password)
            case '3':
                return -1
            case _:
                print("Unavailable. Please try again")
                return start(conn)
    except Exception as e:
        print(e)
    return start(conn)

def build_example_data(conn):
    Employee_Functions.add_user(conn, "admin", "password")
    Employee_Functions.add_user(conn, "user1", "password1")
    Employee_Functions.add_user(conn, "user2", "password2")

    Employee_Functions.add_expense(conn, 1, 500, "consultant", Category(6).name)
    Employee_Functions.add_expense(conn, 1, 1000, "3 day stay", Category(2).name)
    Employee_Functions.add_expense(conn, 1, 1500, "lunch", Category(3).name)

    Employee_Functions.add_expense(conn, 2, 1000, "glitchy comma key", Category(1).name)
    Employee_Functions.add_expense(conn, 3, 1500, "round trip", Category(2).name)

def expense_description(expense: tuple):
    description = ["Expense ID", "Amount", "Description", "Category", "Date", "Status", "Comment"]
    ex = {"Expense ID": expense[0]}
    i = 2
    while i < len(expense):
        ex[description[i - 1]] = expense[i]
        i += 1
    return ex

def parse_expense(args: list):
    expenses = {}
    if args is None:
        print("No Expenses Available")
        return None
    else:
        i = 1
        for arg in args:
            expenses[i] = expense_description(arg)
            i += 1
        print(pd.DataFrame(expenses.values()).to_markdown(index=expenses.keys()))
        return expenses

def selectCategory():
    print("Select Category")
    for name, mem in Category.__members__.items():
        print(f"{mem.value}. {name}")
    cat = input("Enter Category Number: ")
    try:
        return Category(int(cat)).name if cat != "" else ""
    except ValueError:
        print("Please choose a valid category number")
        selectCategory()

def add_expenses(conn, user_id:int ):
    amount = float(input("Enter Amount: "))
    description = input("Enter Description: ")
    category = selectCategory()
    date = input("Enter Date: ")
    if category == "":
        category = Category.Other
    try:
        if date == "":
            result = Employee_Functions.add_expense(conn, user_id, amount, description, category)
        else:
            result = Employee_Functions.add_expense(conn, user_id, amount, description, category, date)
        print("New Expense: ")
        parse_expense(Employee_Functions.view_expense(conn, result))
    except Exception as e:
        print(e)

def view_expenses(conn, user_id):
    parse_expense(Employee_Functions.view_all_expenses(conn, user_id))

def verify_selection(action:str, expenses: dict):
    expense_id = input(f"Please choose an expense to {action}: ")
    try:
        expense_id = int(expense_id)
        if expense_id > len(expenses):
            print("Please choose a valid expense ID")
            return False
        return expense_id

    except ValueError:
        print("Please choose a valid expense ID")
        return False

def modify_expenses(conn, user_id: int):
    expenses = parse_expense(Employee_Functions.get_pending_expenses(conn, user_id))
    if expenses is not None:
        expense_selection = verify_selection("modify", expenses)
        if expense_selection == False:
            modify_expenses(conn, user_id)
        else:
            amount = input("Enter Amount: ")
            description = input("Enter Description: ")
            category = selectCategory()
            date = input("Enter Date: ")

            if amount == "":
                amount = expenses[expense_selection]["Amount"]
            else:
                amount = float(amount)
            if description == "":
                description = expenses[expense_selection]["Description"]
            if category == "":
                category = expenses[expense_selection]["Category"]
            if date == "":
                date = expenses[expense_selection]["Date"]

            expense_id = expenses[expense_selection]["Expense ID"]
            Employee_Functions.modify_expense(conn, user_id, expense_id, amount, description, category, date)

def delete_expenses(conn, user_id : int):
    expenses = parse_expense(Employee_Functions.get_pending_expenses(conn, user_id))
    if expenses is not None:
        #print(*expenses.items(), sep="\n")
        expense_selection = verify_selection("delete", expenses)
        if expense_selection == False:
            delete_expenses(conn, user_id)
        else:
            expense_id = expenses[expense_selection]["Expense ID"]
            Employee_Functions.delete_expense(conn, user_id, expense_id)

def view_complete_expenses(conn, user_id: int):
    expenses = Employee_Functions.view_completed_expenses(conn, user_id)
    if expenses is None:
        print("No Completed Expenses")
    else:
        parse_expense(expenses)

def actions():
    li = [
        "1. Submit New Expense",
        "2. View Expenses",
        "3. Edit Pending Expense",
        "4. Delete Pending Expense",
        "5. View Completed Expenses",
        "6. Log Out"
    ]
    print(*li, sep="\n")
    action = input()
    try:
        return int(action)
    except ValueError:
        return -1

def main():
    conn = connect_employee_db()

    user = None
    while user != -1:

        if user is None:
            print("Please Select an Option: ")
            user = start(conn)
        else:
            print("Welcome")
            user_action = actions()
            match user_action:
                case 1: add_expenses(conn, user)
                case 2: view_expenses(conn, user)
                case 3: modify_expenses(conn, user)
                case 4: delete_expenses(conn, user)
                case 5: view_complete_expenses(conn, user)
                case 6:
                    print("Goodbye")
                    user = None
                case _:
                    print("Unavailable. Please try again")

    return -1

if __name__ == '__main__':
    main()