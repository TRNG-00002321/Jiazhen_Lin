"""
To-Do List Manager: Create a program to add, view, mark as complete, and delete tasks from a list/dictionary, potentially saving the list/dictionary to a file.
Please use the following as well while writing code
1. Functions
2. Modules (Optional)
3. Imports (Optional)
"""

class ToDoList:
    def __init__(self):
        self.to_do_list = {}

    def add_task(self, task):
        self.to_do_list.update({task:False})

    def view_tasks(self):
        print(*[ f"{str(k).ljust(30)} {str(v).ljust(5)}" for k, v in self.to_do_list.items()], sep ="\n")

    def mark_complete(self, task):
        self.to_do_list[task] = True

    def delete_task(self, task):
        self.to_do_list.pop(task)

    def save_to_file(self, filename = "ToDoList.txt"):
        with open(filename, "w") as file:
            for k, v in self.to_do_list.items():
                file.write(f"{str(k).ljust(30)} {str(v).ljust(6)}\n")


l = ToDoList()
l.add_task("Add a task1")
l.add_task("Add a task2")
l.add_task("Add a task3")
l.view_tasks()
print()
l.mark_complete("Add a task1")
l.view_tasks()
print()
l.delete_task("Add a task2")
l.view_tasks()
print()
l.save_to_file("ToDoList.txt")
