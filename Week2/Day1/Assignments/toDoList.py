from flask import Flask, render_template, request, redirect, url_for
import json

app = Flask(__name__, template_folder="templates")
def load_json_lines(filepath):
    data = []
    with open(filepath, 'r') as f:
        for line in f:
            try:
                data.append(json.loads(line.strip()))
            except json.JSONDecodeError as e:
                print(f"Error decoding JSON on line: {line.strip()} - {e}")
    return data

def get_tasks():
    try:
        tasks = {}
        with open('tasks.json','r') as f:
            for line in f.readlines():
                try:
                    tasks.update(json.loads(line))
                except json.JSONDecodeError as e:
                    print(f"Error decoding JSON on line: {line.strip()} - {e}")
        return tasks
    except FileNotFoundError:
        return {}

def add_task_to_file(task):
    task = {task:'Pending'}
    with open('tasks.json', 'a') as f:
        json.dump(task, f)
        f.write("\n")

def edit_file(tasks):
    with open('tasks.json', 'w') as f:
        for task, status in tasks.items():
            json.dump({task:status}, f)
            f.write("\n")

@app.route('/')
def view_tasks():
    tasks = get_tasks()
    length = len(tasks)
    return render_template('view.html', len=length, tasks=tasks)

@app.route('/add', methods=['POST'])
def add_task():
    task = request.form['task']
    add_task_to_file(task)
    return redirect(url_for('view_tasks'))
@app.route('/edit')
def update_task():
    task = request.values['update'].split(" ",1)[1]
    tasks = get_tasks()
    tasks[task] = 'Completed'
    edit_file(tasks)
    return redirect(url_for('view_tasks'))

@app.route('/delete')
def delete_task():
    task = request.values['update'].split(" ",1)[1]
    tasks = get_tasks()
    tasks.pop(task)
    edit_file(tasks)
    return redirect(url_for('view_tasks'))

if __name__ == "__main__":
    app.run(debug=True)