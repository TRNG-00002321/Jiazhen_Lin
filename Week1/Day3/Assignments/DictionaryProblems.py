"""
Dictionary
1.Given an array arr[], find the first repeating element. T
he element should occur more than once and the index of its first occurrence should be the smallest.
Examples:
Input: arr[] = [1, 5, 3, 4, 3, 5, 6]
Output: 2
Explanation: 5 appears twice and its first appearance is at index 2
which is less than 3 whose first the occurring index is 3.

"""

#question 1

arr = input().split()

def find_first_repeating(arr):
    for i in range(0, len(arr)):
        if arr.count(arr[i]) > 1:
            return i
    return  -1

print(find_first_repeating(arr))