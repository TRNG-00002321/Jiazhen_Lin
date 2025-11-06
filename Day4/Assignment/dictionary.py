import random
from functools import reduce

#1. Create a dictionary of five countries and their capitals.
# Write a function that takes a country name as input and returns its capital.
capitals={
    "US":"Washington",
    "France":"Paris",
    "China":"Beijing",
    "Russia":"Moscow",
    "England":"London",
}
def get_capital(country):
    return capitals[country]

print(get_capital(random.choice(list(capitals.keys()))))
print()
#2. Make a dictionary of student names and their scores.
# Write a function to find the student with the highest score.
scores={
    'a':1,
    'b':2,
    'c':3,
    'd':4,
}
def get_high_score(scores_list):
    return reduce(lambda x,y: x if x>y else y, scores_list.values())

print(get_high_score(scores))
#3. Create a nested dictionary of three employees
# each with keys for name, age, and salary.
# Write a function to give each employee a 10% raise and print the updated dictionary.
employees = {
    1: {
        'Name' : 'Ram',
        'Age' : 23,
        'Salary': 150
    },
    2: {
        'Name' : 'Alice',
        'Age' : 30,
        'Salary': 200
    },
    3: {
        'Name' : 'Bob',
        'Age' : 33,
        'Salary': 250
    }
}

def give_raise(employee_list):
    for num, employee in employee_list.items():
        employee['Salary'] += employee['Salary'] *.1
    return employee_list

print(*give_raise(employees).values(), sep="\n")
print()
"""
4. Write a Python program to add a key to a dictionary
Sample Output
dictionary = {"Name" : "Ram" , "Age" : 23}
add_key = {"City" : "Salem"}
dictionary = {'Name' : 'Ram', 'Age' : 23, 'City' : 'Salem'}
"""
def add_key(dictionary, key_pair):
    dictionary.update(key_pair)
    return dictionary

dictionary = {"Name" : "Ram" , "Age" : 23}
key = {"City" : "Salem"}
print(add_key(dictionary, key))
print()
"""
5. Write a Python program to concatenate following dictionaries to create a new one.
Sample Output
Dictionary 1 = {"Name" : "Ram" , "Age" : 23}
Dictionary 2 = {"City" : "Salem", "Gender" : "Male"}
Concatenate Dictionaries = {'Name' : 'Ram', 'Age' : 23, 'City' : 'Salem', 'Gender': 'Male'}
"""
def combine_dictionaries(dictionaries):
    # main = {}
    # for dictionary in dictionaries:
    #    for pair in dictionary:
    #        main.update({pair : dictionary[pair]})
    # return main
    return {key:value for dictionary in dictionaries for key, value in dictionary.items()}
"""
def combine_dictionaries(dicts):
    return {k: v for d in dicts for k, v in d.items()}

"""
dictionary_1 = {"Name" : "Ram" , "Age" : 23}
dictionary_2 = {"City" : "Salem", "Gender" : "Male"}
print(combine_dictionaries([dictionary_1, dictionary_2]))
print()
"""
6. Write a Python program to check whether a given key already exists in a dictionary.
Sample Output
{'Name' : 'Ram', 'Age' : 23,}
Key = Name
Key is Available in the Dictionary
"""
def find_key(dictionary, key):
    if key in dictionary:
        print("Key is Available in the Dictionary")

d = {'Name' : 'Ram', 'Age' : 23,}
k = "Name"
find_key(d, k)
print()
"""
7. Write a Python program to iterate over dictionaries using for loops.
Sample Output
{"Name" : "Ram" , "Age" : 23 , "City" : "Salem", "Gender" : "Male"}
Name : Ram
Age : 23
City : Salem
Gender : Male
"""
def iterate_over_dictionary(dictionary):
    for k, v in dictionary.items():
        print(f"{k} : {v}")

dictionary = {"Name" : "Ram" , "Age" : 23 , "City" : "Salem", "Gender" : "Male"}
iterate_over_dictionary(dictionary)