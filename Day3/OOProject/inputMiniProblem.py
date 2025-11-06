x = input()
y = input()

try:
    z = int(x)/int(y)
    print(z)
except ZeroDivisionError:
    print("Please enter a number greater than zero for second number")
except ValueError:
    print("Please enter a number for both places")
