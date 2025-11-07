class Animal:
    def __init__(self, name):
        self.name = name

    def speak(self):
        print(f"{self.name} makes a sound")

class Cat(Animal):
    def __init__(self, name, color):
        super().__init__(name)
        self.color = color

    def describe(self):
        print(f"{self.name} is a {self.color} cat")

cat = Cat("cat", "blue")
cat.describe()