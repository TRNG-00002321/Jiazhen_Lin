import mysql.connector
def build_employee_db():
    conn = mysql.connector.connect(
        host='localhost',
        port=3306,
        user='root',
        password='password',
    )
    try:
        cursor = conn.cursor()
        cursor.execute("CREATE DATABASE IF NOT EXISTS employee")
        cursor.close()
        conn.close()

        conn = mysql.connector.connect(
            host='localhost',
            user='root',
            password='password',
            database='employee',
            autocommit=True
        )

        cursor = conn.cursor()
        with open("database.sql", 'r') as f:
            sql_script = f.read()

        statements = sql_script.split(';')
        for stmt in statements:
            stmt = stmt.strip()
            if stmt:
                cursor.execute(stmt)
        conn.commit()

    except mysql.connector.Error as e:
        conn.close()
        raise mysql.connector.Error(f"Error executing SQL: {e}")
    return conn

def connect_employee_db():

    try:
        conn = mysql.connector.connect(
            host='localhost',
            user='root',
            password='password',
            database='employee'
        )
        return conn
    except mysql.connector.Error as e:
        raise mysql.connector.Error(f"No connection made")
