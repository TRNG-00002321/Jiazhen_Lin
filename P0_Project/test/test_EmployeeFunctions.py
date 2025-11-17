import os
from parameterized import parameterized
import sqlite3
import unittest

from ..src.Employee_Functions import EmployeeFunctions

test_dir = os.path.dirname(os.path.abspath(__file__))
db_path = os.path.join(test_dir, "..", "src", "database.sql")
#need __init__.py file, can be empty to do ^

class TestEmployeeFunctions(unittest.TestCase):
    def test_canary(self):
        self.assertTrue(True)

    def set_up(self):
        self.conn = sqlite3.connect(":memory:") # In-memory database for testing
        with open(db_path, "r") as f:
            sql_script = f.read()
            self.conn.executescript(sql_script)
            self.conn.commit()

    def tear_down(self):
        self.conn.close()

    @parameterized.expand([
        ["admin", "password"],
        ["admin1", "password"]
    ])
    def test_new_employee(self, username, password):
        self.set_up()
        EmployeeFunctions.add_user(self.conn, username, password)
        cursor = self.conn.cursor()
        cursor.execute("SELECT * FROM users WHERE username = ?", (username,))
        result = cursor.fetchone()
        self.assertIsNotNone(result)
        self.assertEqual(result[1], username)
        self.tearDown()

    def test_new_employee_no_duplicates(self):
        self.set_up()
        EmployeeFunctions.add_user(self.conn, "admin", "password")
        self.assertRaises(ValueError, EmployeeFunctions.add_user, self.conn, "admin", "password")
        self.tearDown()

    @parameterized.expand([
        [None, "password"],
        ["admin", None],
        [None, None],
        ["", "test"],
        ["name", ""],
        ["ubvyeoaubveaohasbdvaldsjbvivb", "vladnvlabj"]
    ])
    def test_new_employee_errors(self, username, password):
        self.set_up()
        self.assertRaises(ValueError, EmployeeFunctions.add_user, self.conn, username, password)
        self.tearDown()

    @parameterized.expand([
        ["admin", "password123"],
        ["jHJB324y!#41", "  mypass"],
        ["test  user", "!#$!@sdvasfADFSA324  "],
    ])
    def test_log_in_success(self, username, password):
        self.set_up()
        EmployeeFunctions.add_user(self.conn, username, password)
        user_id = EmployeeFunctions.log_in(self.conn, username, password)

        self.assertIsNotNone(user_id)
        self.assertIsInstance(user_id, int)
        self.assertGreater(user_id, 0)
        self.tearDown()

    @parameterized.expand([
        ["admin", "Password123", "password123"],
        ["jHJB324y!#41", "  mypass  ", None],
        ["", "test", "test"],
        [None, None, "hello"],
    ])
    def test_log_in_failed(self, username, password, invalid_password):
        self.set_up()
        if username and password: EmployeeFunctions.add_user(self.conn, username, password)
        self.assertRaises(ValueError, EmployeeFunctions.log_in, self.conn, username, invalid_password)
        self.tearDown()

    @parameterized.expand([
        ["dsfa", "dsfas", 30],
        ["casdv", "Afas", 0.00],
        ["adfd", "ASf", -30.63]
    ])
    def test_new_expense(self, username, password, expense):
        self.set_up()
        EmployeeFunctions.add_user(self.conn, username, password)
        user_id = EmployeeFunctions.log_in(self.conn, username, password)
        EmployeeFunctions.add_expense(self.conn,user_id, expense)
        cursor = self.conn.cursor()
        cursor.execute("SELECT * FROM expenses")
        result = cursor.fetchone()
        self.assertIsNotNone(result)
        self.assertEqual(result[1], user_id)
        self.assertEqual(result[2], expense)
        self.tearDown()

    def test_new_expense_no_user(self):
        self.set_up()
        self.assertRaises(ValueError, EmployeeFunctions.add_expense, self.conn, 0, 30.0)
        self.tearDown()

    def test_new_expense_no_expense(self):
        self.set_up()
        self.assertRaises(ValueError, EmployeeFunctions.add_expense, self.conn, 0, 0)
        self.tearDown()

    def test_new_expense_invalid_amount(self):
        self.set_up()
        self.assertRaises(ValueError, EmployeeFunctions.add_expense, self.conn, 0, 0, 0.14131)
        self.tearDown()

    @parameterized.expand([
        [30],
        [100]
    ])
    def test_view_expense(self, amount):
        self.set_up()
        EmployeeFunctions.add_user(self.conn, "admin", "password")
        user_id = EmployeeFunctions.log_in(self.conn, "admin", "password")
        EmployeeFunctions.add_expense(self.conn, user_id, amount)
        expense = EmployeeFunctions.view_submitted_expenses(self.conn, user_id)
        self.assertIsNotNone(expense)
        self.assertEqual(expense[0][2], amount)
        self.tearDown()

    @parameterized.expand([
        [30],
        [100]
    ])
    def test_view_expense_multiple(self, amount):
        self.set_up()
        EmployeeFunctions.add_user(self.conn, "admin", "password")
        user_id = EmployeeFunctions.log_in(self.conn, "admin", "password")
        EmployeeFunctions.add_expense(self.conn, user_id, 200)
        EmployeeFunctions.add_expense(self.conn, user_id, amount)
        expense = EmployeeFunctions.view_submitted_expenses(self.conn, user_id)
        self.assertIsNotNone(expense)
        self.assertEqual(expense[1][2], amount)
        self.tearDown()

    def test_view_expense_no_data(self):
        self.set_up()
        EmployeeFunctions.add_user(self.conn, "admin", "password")
        user_id = EmployeeFunctions.log_in(self.conn, "admin", "password")
        expense = EmployeeFunctions.view_submitted_expenses(self.conn, user_id)
        self.assertIsNone(expense)

    @parameterized.expand([
        [30.00],
        [30.15]
    ])
    def test_modify_expense(self, amount):
        self.set_up()
        EmployeeFunctions.add_user(self.conn, "admin", "password")
        user_id = EmployeeFunctions.log_in(self.conn, "admin", "password")
        expense_id = EmployeeFunctions.add_expense(self.conn, user_id, 200)
        EmployeeFunctions.modify_expense(self.conn, user_id, expense_id, amount)
        expense = EmployeeFunctions.view_submitted_expenses(self.conn, user_id)
        self.assertEqual(expense[0][2], amount)
        self.tearDown()

    def test_modify_expense_not_pending(self):
        self.set_up()

        EmployeeFunctions.add_user(self.conn, "admin", "password")
        user_id = EmployeeFunctions.log_in(self.conn, "admin", "password")
        self.assertRaises(ValueError, EmployeeFunctions.modify_expense, self.conn, user_id, 0, 0)

        EmployeeFunctions.add_expense(self.conn, user_id, 100)
        expense_id = EmployeeFunctions.add_expense(self.conn, user_id, 200)
        cursor = self.conn.cursor()
        cursor.execute("UPDATE approvals SET status = 'approved' WHERE expense_id = ?", (expense_id,))
        self.assertRaises(ValueError, EmployeeFunctions.modify_expense, self.conn, user_id, expense_id, 0)
        self.tearDown()

    @parameterized.expand([
        [30.00],
        [30.15]
    ])
    def test_delete_expense(self, amount):
        self.set_up()
        EmployeeFunctions.add_user(self.conn, "admin", "password")
        user_id = EmployeeFunctions.log_in(self.conn, "admin", "password")
        expense_id = EmployeeFunctions.add_expense(self.conn, user_id, 200)
        EmployeeFunctions.delete_expense(self.conn, user_id, expense_id)
        expense = EmployeeFunctions.view_submitted_expenses(self.conn, user_id)
        self.assertEqual(expense, None)
        self.tearDown()

    def test_delete_expense_not_pending(self):
        self.set_up()

        EmployeeFunctions.add_user(self.conn, "admin", "password")
        user_id = EmployeeFunctions.log_in(self.conn, "admin", "password")
        self.assertRaises(ValueError, EmployeeFunctions.delete_expense, self.conn, user_id, 0)

        EmployeeFunctions.add_expense(self.conn, user_id, 100)
        expense_id = EmployeeFunctions.add_expense(self.conn, user_id, 200)
        cursor = self.conn.cursor()
        cursor.execute("UPDATE approvals SET status = 'approved' WHERE expense_id = ?", (expense_id,))
        self.assertRaises(ValueError, EmployeeFunctions.delete_expense, self.conn, user_id, expense_id)
        self.tearDown()

    def test_view_completed_expense(self):
        self.set_up()
        EmployeeFunctions.add_user(self.conn, "admin", "password")
        user_id = EmployeeFunctions.log_in(self.conn, "admin", "password")
        expense_id = EmployeeFunctions.add_expense(self.conn, user_id, 200)

        cursor = self.conn.cursor()
        cursor.execute("UPDATE approvals SET status = 'approved' WHERE expense_id = ?", (expense_id,))
        expense_id = EmployeeFunctions.add_expense(self.conn, user_id, 100)
        cursor.execute("UPDATE approvals SET status = 'denied' WHERE expense_id = ?", (expense_id,))
        expense = EmployeeFunctions.view_completed_expenses(self.conn, user_id)
        self.assertIsNotNone(expense)
        self.assertEqual(expense[0][2], 200)
        self.assertEqual(expense[1][2], 100)
        self.tearDown()


if __name__ == "__main__":
    unittest.main()
