"""
Filter and Reduce Assignment
1. Filter Strings by Length
Given a list of strings, use filter() to create a new list containing only the strings with a length greater than 5.
Example
words = ["apple", "banana", "cat", "dog", "elephant", "frog"]

2. Filter Students by Grade
Given a list of dictionaries, where each dictionary represents a student with name and grade keys, use filter() to extract students with a grade of 90 or higher.
Example
students = [
    {"name": "Alice", "grade": 85},
    {"name": "Bob", "grade": 92},
    {"name": "Charlie", "grade": 78},
    {"name": "David", "grade": 95}
]

3. Concatenate Strings
Given a list of strings, concatenate them into a single string using reduce.
Example :
	words = ["Python", "is", "awesome", "!"]

4. Find the Maximum Element
Given a list of numbers, find the maximum element using reduce.
Example:
	numbers = [10, 3, 25, 7, 18]

5. Flatten a List of Lists
Given a list of lists, flatten it into a single list using reduce.
Example:
	list_of_lists = [[1, 2], [3, 4], [5, 6]]
"""
from functools import reduce

#1. Filter Strings by Length
words = ["apple", "banana", "cat", "dog", "elephant", "frog"]
newWords = list(filter(lambda word: len(word) > 5, words))
print(newWords)

#2. Filter Students by Grade
students = [
    {"name": "Alice", "grade": 85},
    {"name": "Bob", "grade": 92},
    {"name": "Charlie", "grade": 78},
    {"name": "David", "grade": 95}
]
aStudents = filter(lambda student: student["grade"] >= 90, students)
print(list(i['name'] for i in aStudents))

#3. Concatenate Strings
words = ["Python", "is", "awesome", "!"]
sentence = reduce(lambda x, y: x + " " + y, words)
print(sentence)

#4. Find the Maximum Element
numbers = [10, 3, 25, 7, 18]
max_num = reduce(lambda x, y: x if x>y else y, numbers)
print(max_num)

#5. Flatten a List of Lists
list_of_lists = [[1, 2], [3, 4], [5, 6]]
all = reduce(lambda x, y: x + y, list_of_lists)
print(all)
