class Person:
    def __init__(self, name, age): #constructor
        self.name = name
        self.age = age

    def __str__(self): #used when class is printed
        return f"{self.name} is {self.age} years old"

    def greet(self):
        print(f"Hello my name is, {self.name}!")

p1 = Person("John", 25)
p1.greet()

print(p1)
