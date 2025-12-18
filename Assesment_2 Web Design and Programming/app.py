from flask import Flask, request, render_template, flash, session, redirect
import sqlite3
import hashlib
from functools import wraps

app = Flask(__name__)
app.secret_key = "simplecrm_secret_key_change_me"


# Helpers

def roles_permitted(roles):
    def decorator(f):
        @wraps(f)
        def wrapper(*args, **kwargs):
            if 'uid' in session and session.get('role') in roles:
                return f(*args, **kwargs)
            flash(f"ERROR: you need {roles} role to access this page")
            return redirect('/login')
        return wrapper
    return decorator


def get_db_conn():
    db = sqlite3.connect('crm.db')
    db.row_factory = sqlite3.Row
    return db


def hash_password(username, password):
    pw = (username + password).encode('utf-8')
    return hashlib.sha512(pw).hexdigest()


def initialize_db():
    db = get_db_conn()
    c = db.cursor()
    c.execute("PRAGMA foreign_keys=ON")

    c.execute("""
        CREATE TABLE IF NOT EXISTS users (
            uid INTEGER PRIMARY KEY AUTOINCREMENT,
            username TEXT NOT NULL UNIQUE,
            password TEXT NOT NULL,
            role TEXT NOT NULL DEFAULT 'employee',
            is_active TEXT NOT NULL DEFAULT 'active',
            created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP
        )
    """)

    c.execute("""
        CREATE TABLE IF NOT EXISTS customers (
            cid INTEGER PRIMARY KEY AUTOINCREMENT,
            created_by_user_id INTEGER NOT NULL,
            first_name TEXT NOT NULL,
            last_name TEXT NOT NULL,
            email TEXT NOT NULL,
            status TEXT NOT NULL DEFAULT 'Lead',
            is_active TEXT NOT NULL DEFAULT 'active',
            created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
            FOREIGN KEY(created_by_user_id) REFERENCES users(uid)
        )
    """)

    db.commit()
    db.close()


# Public pages

@app.route('/')
def home():
    return render_template("home.html")


@app.route('/register', methods=['GET', 'POST'])
def register():
    username = ''
    if request.method == 'POST':
        username = request.form['username'].strip()
        password = request.form['password']
        password2 = request.form['password2']

        if password != password2:
            flash("ERROR: Passwords do not match")
            return render_template('register_form.html', username=username)

        db = get_db_conn()
        user = db.execute("SELECT * FROM users WHERE username=?", (username,)).fetchone()
        if user:
            db.close()
            flash("ERROR: Username is taken")
            return render_template('register_form.html', username=username)

        hashed = hash_password(username, password)
        db.execute("INSERT INTO users (username, password, role) VALUES (?, ?, ?)",
                   (username, hashed, 'employee'))
        db.commit()
        db.close()
        return redirect('/login')

    return render_template('register_form.html', username=username)


@app.route('/login', methods=['GET', 'POST'])
def login():
    username = ''
    if request.method == 'POST':
        username = request.form['username'].strip()
        password = request.form['password']

        db = get_db_conn()
        user = db.execute("SELECT * FROM users WHERE username=?", (username,)).fetchone()
        db.close()

        if not user:
            flash("ERROR: username not found")
            return render_template('login_form.html', username=username)

        if user['is_active'] != 'active':
            flash("ERROR: user is blocked/disabled")
            return render_template('login_form.html', username=username)

        hashed = hash_password(username, password)
        if user['password'] != hashed:
            flash("ERROR: wrong credentials")
            return render_template('login_form.html', username=username)

        session['uid'] = user['uid']
        session['username'] = user['username']
        session['role'] = user['role']

        if user['role'] == 'admin':
            return redirect('/admin')
        if user['role'] == 'manager':
            return redirect('/manager')
        return redirect('/employee')

    return render_template('login_form.html', username=username)


@app.route('/logout')
def logout():
    session.clear()
    return redirect('/login')


# Dashboards

@app.route('/employee')
@roles_permitted(['employee'])
def employee_dashboard():
    return render_template('employee_dashboard.html')


@app.route('/manager')
@roles_permitted(['manager'])
def manager_dashboard():
    return render_template('manager_dashboard.html')


@app.route('/admin')
@roles_permitted(['admin'])
def admin_dashboard():
    return render_template('admin_dashboard.html')



# Employee: customes

@app.route('/customers')
@roles_permitted(['employee'])
def customers():
    db = get_db_conn()
    rows = db.execute(
        "SELECT * FROM customers WHERE created_by_user_id=? ORDER BY cid DESC",
        (session['uid'],)
    ).fetchall()
    db.close()
    return render_template('customers.html', customers=rows)


@app.route('/add/customer', methods=['GET', 'POST'])
@roles_permitted(['employee'])
def add_customer():
    if request.method == 'POST':
        first_name = request.form['first_name'].strip()
        last_name = request.form['last_name'].strip()
        email = request.form['email'].strip()

        db = get_db_conn()
        db.execute("""
            INSERT INTO customers (created_by_user_id, first_name, last_name, email, status, is_active)
            VALUES (?, ?, ?, ?, ?, ?)
        """, (session['uid'], first_name, last_name, email, 'Lead', 'active'))
        db.commit()
        db.close()

        return redirect('/customers')

    return render_template('add_customer.html')


@app.route('/customer/<int:cid>')
@roles_permitted(['employee'])
def view_customer(cid):
    db = get_db_conn()
    customer = db.execute(
        "SELECT * FROM customers WHERE cid=? AND created_by_user_id=?",
        (cid, session['uid'])
    ).fetchone()
    db.close()

    if not customer:
        return "Customer not found", 404

    return render_template('view_customer.html', customer=customer)


@app.route('/customer/<int:cid>/disable', methods=['POST'])
@roles_permitted(['employee'])
def disable_customer(cid):
    db = get_db_conn()
    customer = db.execute(
        "SELECT * FROM customers WHERE cid=? AND created_by_user_id=?",
        (cid, session['uid'])
    ).fetchone()

    if not customer:
        db.close()
        return "Customer not found", 404

    db.execute("UPDATE customers SET is_active='disabled' WHERE cid=?", (cid,))
    db.commit()
    db.close()
    return redirect('/customers')


# ADMIN: CRUD USERS-users list / blocked

@app.route('/users')
@roles_permitted(['admin'])
def users():
    db = get_db_conn()
    users = db.execute("SELECT * FROM users").fetchall()
    db.close()
    return render_template('users.html', users=users)


@app.route('/users/add', methods=['GET', 'POST'])
@roles_permitted(['admin'])
def add_user():
    if request.method == 'POST':
        username = request.form['username']
        role = request.form['role']
        password = '123'  # default simple password

        hashed = hash_password(username, password)

        db = get_db_conn()
        db.execute(
            "INSERT INTO users (username, password, role, is_active) VALUES (?, ?, ?, 'active')",
            (username, hashed, role)
        )
        db.commit()
        db.close()

        return redirect('/users')

    return render_template('user_add.html')

@app.route('/users/blocked')
@roles_permitted(['admin'])
def blocked_users():
    db = get_db_conn()
    cursor = db.cursor()

    users = cursor.execute(
        "SELECT * FROM users WHERE is_active='blocked'"
    ).fetchall()

    db.close()
    return render_template('blocked_users.html', users=users)


@app.route('/users/<int:uid>/edit', methods=['GET', 'POST'])
@roles_permitted(['admin'])
def edit_user(uid):
    db = get_db_conn()
    user = db.execute("SELECT * FROM users WHERE uid=?", (uid,)).fetchone()

    if not user:
        db.close()
        return "User not found", 404

    if request.method == 'POST':
        role = request.form['role']
        is_active = request.form['is_active']

        db.execute(
            "UPDATE users SET role=?, is_active=? WHERE uid=?",
            (role, is_active, uid)
        )
        db.commit()
        db.close()
        return redirect('/users')

    db.close()
    return render_template('user_edit.html', user=user)


@app.route('/users/<int:uid>/block', methods=['POST'])
@roles_permitted(['admin'])
def block_user(uid):
    db = get_db_conn()
    db.execute("UPDATE users SET is_active='blocked' WHERE uid=?", (uid,))
    db.commit()
    db.close()
    return redirect('/users')



if __name__ == '__main__':
    initialize_db()
    app.run(debug=True)
