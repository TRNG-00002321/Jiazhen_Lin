import os
from parameterized import parameterized
import mysql.connector
import unittest

from ..src import Employee_Functions as EmployeeFunctions

test_dir = os.path.dirname(os.path.abspath(__file__))
db_path = os.path.join(test_dir, "..", "src", "database.sql")
#need __init__.py file, can be empty to do ^

class TestEmployeeFunctions(unittest.TestCase):
    def test_canary(self):
        self.assertTrue(True)

    def set_up(self):
        self.conn = mysql.connector.connect(
            host='localhost',
            port=3306,
            user='root',
            password='password',
        )

        cursor = self.conn.cursor()
        cursor.execute("DROP DATABASE IF EXISTS test")
        cursor.execute("CREATE DATABASE test")
        self.conn.close()

        self.conn = mysql.connector.connect(
            host='localhost',
            user='root',
            password='password',
            database='test',
        )

        cursor = self.conn.cursor()
        with open(db_path, 'r') as f:
            sql_script = f.read()

        statements = sql_script.split(';')
        for stmt in statements:
            stmt = stmt.strip()
            if stmt:
                cursor.execute(stmt)
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
        cursor.execute("SELECT * FROM users WHERE username = %s", (username,))
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
        ["tryAVeryLongAnnoyingUsername", "password"]
    ])
    def test_new_employee_errors(self, username, password):
        self.set_up()
        self.assertRaises(ValueError, EmployeeFunctions.add_user, self.conn, username, password)
        self.tearDown()

    @parameterized.expand([
        ["admin", "password123"],
        ["jHJB324y!#41", "  mypass"],
        ["test  user", "!#$!@randomADFSA324  "],
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
        ["test1", "pass1", 30],
        ["test2", "pass2", .01],
        ["test3", "pass3", 30.63]
    ])
    def test_new_expense(self, username, password, expense):
        self.set_up()
        EmployeeFunctions.add_user(self.conn, username, password)
        user_id = EmployeeFunctions.log_in(self.conn, username, password)
        EmployeeFunctions.add_expense(self.conn,user_id, expense, "TEST", "TEST")
        cursor = self.conn.cursor()
        cursor.execute("SELECT * FROM expenses")
        result = cursor.fetchone()
        self.assertIsNotNone(result)
        self.assertEqual(result[1], user_id)
        self.assertEqual(result[2], expense)
        self.tearDown()

    def test_new_expense_no_user(self):
        self.set_up()
        self.assertRaises(ValueError, EmployeeFunctions.add_expense, self.conn, 0, 30.0, "no user", "TEST")
        self.tearDown()

    def test_new_expense_no_expense(self):
        self.set_up()
        EmployeeFunctions.add_user(self.conn, "admin", "password")
        user_id = EmployeeFunctions.log_in(self.conn, "admin", "password")
        self.assertRaises(ValueError, EmployeeFunctions.add_expense, self.conn, user_id, 0, "TEST", "TEST")
        self.tearDown()

    @parameterized.expand([
        [0.13412],
        [-133.12]
    ])
    def test_new_expense_invalid_amount(self, amount):
        self.set_up()
        EmployeeFunctions.add_user(self.conn, "admin", "password")
        user_id = EmployeeFunctions.log_in(self.conn, "admin", "password")
        self.assertRaises(ValueError, EmployeeFunctions.add_expense, self.conn, user_id, amount, "TEST", "Test")
        self.tearDown()

    @parameterized.expand([
        [30],
        [100]
    ])
    def test_view_expense(self, amount):
        self.set_up()
        EmployeeFunctions.add_user(self.conn, "admin", "password")
        user_id = EmployeeFunctions.log_in(self.conn, "admin", "password")
        EmployeeFunctions.add_expense(self.conn, user_id, amount, "test", "test")
        expense = EmployeeFunctions.view_all_expenses(self.conn, user_id)
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
        EmployeeFunctions.add_expense(self.conn, user_id, 200, "test", "test")
        EmployeeFunctions.add_expense(self.conn, user_id, amount, "test", "test")
        expense = EmployeeFunctions.view_all_expenses(self.conn, user_id)
        self.assertIsNotNone(expense)
        self.assertEqual(expense[1][2], amount)
        self.tearDown()

    def test_view_expense_no_data(self):
        self.set_up()
        EmployeeFunctions.add_user(self.conn, "admin", "password")
        user_id = EmployeeFunctions.log_in(self.conn, "admin", "password")
        expense = EmployeeFunctions.view_all_expenses(self.conn, user_id)
        self.assertIsNone(expense)

    @parameterized.expand([
        [30.00],
        [30.15]
    ])
    def test_modify_expense(self, amount):
        self.set_up()
        EmployeeFunctions.add_user(self.conn, "admin", "password")
        user_id = EmployeeFunctions.log_in(self.conn, "admin", "password")
        expense_id = EmployeeFunctions.add_expense(self.conn, user_id, 200, "test", "test")
        EmployeeFunctions.modify_expense(self.conn, user_id, expense_id, amount, "test", "test")
        expense = EmployeeFunctions.view_all_expenses(self.conn, user_id)
        self.assertEqual(expense[0][2], amount)
        self.tearDown()

    def test_modify_expense_not_pending(self):
        self.set_up()

        EmployeeFunctions.add_user(self.conn, "admin", "password")
        user_id = EmployeeFunctions.log_in(self.conn, "admin", "password")
        self.assertRaises(ValueError, EmployeeFunctions.modify_expense, self.conn, user_id, 0, 0, "test", "test")

        EmployeeFunctions.add_expense(self.conn, user_id, 100, "test", "test")
        expense_id = EmployeeFunctions.add_expense(self.conn, user_id, 200, "test", "test")
        cursor = self.conn.cursor()
        cursor.execute("UPDATE approvals SET status = 'approved' WHERE expense_id = %s", (expense_id,))
        self.assertRaises(ValueError, EmployeeFunctions.modify_expense, self.conn, user_id, expense_id, 0, "test", "test")
        self.tearDown()


    def test_delete_expense(self):
        self.set_up()
        EmployeeFunctions.add_user(self.conn, "admin", "password")
        user_id = EmployeeFunctions.log_in(self.conn, "admin", "password")
        expense_id = EmployeeFunctions.add_expense(self.conn, user_id, 200, "TEST", "Tests")
        EmployeeFunctions.delete_expense(self.conn, user_id, expense_id)
        expense = EmployeeFunctions.view_all_expenses(self.conn, user_id)
        cursor = self.conn.cursor()
        cursor.execute("SELECT * FROM approvals")
        self.assertEqual(cursor.fetchall(), [])
        self.assertEqual(expense, None)
        self.tearDown()

    def test_delete_expense_not_pending(self):
        self.set_up()

        EmployeeFunctions.add_user(self.conn, "admin", "password")
        user_id = EmployeeFunctions.log_in(self.conn, "admin", "password")
        self.assertRaises(ValueError, EmployeeFunctions.delete_expense, self.conn, user_id, 0)

        EmployeeFunctions.add_expense(self.conn, user_id, 100, "test", "test")
        expense_id = EmployeeFunctions.add_expense(self.conn, user_id, 200, "test", "test")
        cursor = self.conn.cursor()
        cursor.execute("UPDATE approvals SET status = 'approved' WHERE expense_id = %s", (expense_id,))
        self.assertRaises(ValueError, EmployeeFunctions.delete_expense, self.conn, user_id, expense_id)
        self.tearDown()

    def test_view_completed_expense(self):
        self.set_up()
        EmployeeFunctions.add_user(self.conn, "admin", "password")
        user_id = EmployeeFunctions.log_in(self.conn, "admin", "password")
        expense_id = EmployeeFunctions.add_expense(self.conn, user_id, 200, "test", "test")
        cursor = self.conn.cursor()
        cursor.execute("UPDATE approvals SET status = 'approved' WHERE expense_id = %s", (expense_id,))
        expense_id = EmployeeFunctions.add_expense(self.conn, user_id, 100, "test", "test")
        cursor.execute("UPDATE approvals SET status = 'denied' WHERE expense_id = %s", (expense_id,))
        expense = EmployeeFunctions.view_completed_expenses(self.conn, user_id)
        self.assertIsNotNone(expense)
        self.assertEqual(len(expense), 2)
        self.tearDown()


if __name__ == "__main__":
    unittest.main()
