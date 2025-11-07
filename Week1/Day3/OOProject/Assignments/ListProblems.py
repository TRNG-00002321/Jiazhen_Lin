"""
1.Create a list that contains integers. You need to return the sum of the list.
2. You are given a number k and a list arr[] that contains integers. You need to return list of numbers that are less than k.
Example
Input: arr[] = [54, 43, 2, 1, 5], k = 6
Output: 2 1 5
Explanation: 2 1 5 are less than 6.
3. You are given a list arr that contains integers. You need to return average of the non negative integers.
Examples:
Input: arr = [-12, 8, -7, 6, 12, -9, 14]
Output: avg = 10.0
Explanation: The non negative numbers are 8 6 12 14. The sum is 8+6+12+14 = 40, Average = 40/4 = 10.0

"""
#Question 1
listNum = [int(x) for x in input().split()]
print(sum(listNum))
"""total = 0
for i in listNum:):
    total += i
print(total)
    """
#Question 2
k = int(input())
print([num for num in listNum if num < k]) #using list from question 1

#Question 3
nonNegNum = [num for num in listNum if num > 0]

print(nonNegNum)
print(sum(nonNegNum)/len(nonNegNum))



