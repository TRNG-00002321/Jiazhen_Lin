import sqlite3
def build_employee_db():
    conn = sqlite3.connect('employee.db', check_same_thread=False)
    #remove the need for check same thread somehow
    try:
        with open("database.sql", 'r') as f:
            sql_script = f.read()
        conn.executescript(sql_script)
        conn.commit()

    except sqlite3.Error as e:
        conn.close()
        raise sqlite3.Error(f"Error executing SQL: {e}")

    return conn

def connect_employee_db():

    try:
        conn = sqlite3.connect('employee.db')
        conn.cursor()
        return conn
    except sqlite3.Error as e:
        raise sqlite3.Error(f"No connection made")