"""
Map Assignment

1. Convert Celsius to Fahrenheit: Given a list of temperatures in Celsius, use map() to convert them to Fahrenheit. The formula is F = (C * 9/5) + 32.
Example
    celsius_temps = [0, 10, 20, 30]
    # Expected output: [32.0, 50.0, 68.0, 86.0]

2. Capitalize a List of Names: Given a list of names, use map() to return a new list where each name is capitalized.
Example
    names = ["alice", "bob", "charlie"]
    # Expected output: ["Alice", "Bob", "Charlie"]

3. Add Corresponding Elements: Given two lists of numbers of the same length, use map() to return a new list containing the sum of corresponding elements.
Example
	list1 = [1, 2, 3]
    list2 = [4, 5, 6]
    # Expected output: [5, 7, 9]

4. Concatenate Strings: Given two lists of strings, use map() to concatenate corresponding elements with a space in between.
Example
    first_names = ["John", "Jane"]
    last_names = ["Doe", "Smith"]
    # Expected output: ["John Doe", "Jane Smith"]

5. Apply a Custom Function: Define a function that takes a string and returns its length. Then, use map() to apply this function to a list of strings, returning a list of lengths.
Example
    words = ["apple", "banana", "cherry"]
    # Expected output: [5, 6, 6]
"""
#1
celsius_temps = [0, 10, 20, 30]
fahrenheit_temps = list(map(lambda x: x * 9 / 5 + 32, celsius_temps))
print(fahrenheit_temps)

#2
names = ["alice", "bob", "charlie"]
caps = list(map(lambda x: x.capitalize(), names))
print(caps)

#3
list1 = [1, 2, 3]
list2 = [4, 5, 6]
total = list(map(lambda x, y: x + y, list1, list2))
print(total)

#4
first_names = ["John", "Jane"]
last_names = ["Doe", "Smith"]
full_names = list(map(lambda x, y: x + " " + y, first_names, last_names))
print(full_names)

#5
length = lambda x: len(x)
words = ["apple", "banana", "cherry"]
length_list = list(map(length, words))
print(length_list)

