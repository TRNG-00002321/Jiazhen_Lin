from abc import ABC, abstractmethod

class Shape(ABC):
    @abstractmethod
    def area(self):
        pass

class Rectangle(Shape):
    def __init__(self, height, width):
        self.height = height
        self.width = width

    def area(self):
        return self.height * self.width

class Circle(Shape):
    def __init__(self, radius):
        self.radius = radius

    def area(self):
        return self.radius * self.radius * 3.14

class Triangle(Shape):
    def __init__(self, height, width):
        self.height = height
        self.width = width

    def area(self):
        return (self.height * self.width)/2

circle = Circle(2)
print(circle.area())

triangle = Triangle(3,4)
print(triangle.area())

rectangle = Rectangle(3,4)
print(rectangle.area())