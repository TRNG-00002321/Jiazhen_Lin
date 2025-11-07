"""
1. Given a string s, you need to reverse it.
2. Given a string s, you need to check if it is palindrome or not.
    A palidrome is a string that reads the same from front and back.
3. Given a string s, and a pattern p. You need to find if p exists in s or not and
    return the starting index of p in s. If p does not exist in s then -1 will be returned.
Here p and s both are case-sensitive.

Examples:
Input: s = "Hello", p = "llo"
Output: 2
Explanation: llo starts from the second index in Hello.
"""

s = input()

print(s[::-1]) #question 1

def if_palindrome(s):
    return s == s[::-1]

print(if_palindrome(s)) #question 2

pattern = input()

def if_pattern_in_string(s, pattern):
    return s.index(pattern) if pattern in s else -1

print(if_pattern_in_string(s, pattern))