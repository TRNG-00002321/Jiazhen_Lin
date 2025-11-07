"""1. Given two inputs that are stored in variables a and n,
you need to print a, n times in a single line without space between them.
Output must have a newline at the end.

2. Given three inputs that are stored in the variables a, b, and c.
You need to print a a times and b b times  in a single line separated by c.

3. You need to perform three separate tasks based on the given input:
String Input and Print: Take a text input as a string and print it as it is.
Integer Input and Add: Take an integer input n, add 10 to it, and print the result.
Float Input and Multiply: Take a floating-point number as input, multiply it by 10, and print the result.

4. You are given two integer variables x and y. You need to perform the following operations:

p = x + y : Addition
q = x - y : Subtraction
r = x * y :Multiplication
s = x / y : Division
t = x % y : Modulo

5. Given two numbers a and b, you need to swap their values so a holds the value of b and b holds the value of a. Just write the code to swap values of a and b at the specified place.
"""

#Question 1
a = input()
n = input()

print(a*int(n), "\n")

#question 2

a = int(input())
b = int(input())
c = input()

print(str(a)*a, str(b)*b, sep = c)

#question 3
text = input()
num = int(input())
floatNum = float(input())

print(text)
print(num+10)
print(floatNum*10)

#question 4
x = int(input())
y = int(input())
p = x + y
q = x - y
r = x * y
s = x / y
t = x % y
print(p, q, r, s, t)

#question 5
a = int(input())
b = int(input())

def swap(a, b):
    return b, a

print(swap(a, b))


