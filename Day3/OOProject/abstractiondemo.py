from abc import ABC, abstractmethod

class Shape(ABC): #sole purpose of abstract class is to be inherited
    @abstractmethod
    def shape(self): #let us implementation be provided after/makes subclass define it
        pass

class Circle(Shape):
    def shape(self):
        return "Circle"

class Rectangle(Shape):
    def shape(self):
        return "Rectangle"

circle = Circle()
print(circle.shape())

rectangle = Rectangle()
print(rectangle.shape())