class Animal:
    def speak(self):
        print("Animal Speaks")

class Dog(Animal):
    def speak(self):
        print("Dog Speaks")

d = Dog()
d.speak()