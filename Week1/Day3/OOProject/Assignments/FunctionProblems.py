"""
1. This problem has no input. You need to write the function helloFunction that prints Hello.
2. Here two integers a and b are given. The given input and its values are passed as arguments to the function argumentFunction. The argumentFunction is responsible to return (a+b). You need to write this function.
3. Given a number n, find the first digit of the number.
"""

def hello_function():
    print("Hello")

def argument_function(a, b):
    return a + b

def find_first_digit(a):
    return int(str(a)[0])

hello_function()
print(argument_function(1, 2))
print(find_first_digit(123456789))