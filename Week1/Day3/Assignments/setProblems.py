"""
Set
1. You are given an array arr[] of size n.
You have to insert all elements of arr[] into a set and return that set .You are also given a interger x.
If x is found in set then erase it from set and print "erased x", otherwise, print "not found".
"""
arr = input().split()
x = input()
def custom_set(arr ,x):
    setArr = set(arr)
    if x in setArr:
        print(f"erased {x}")
        setArr.remove(x)
    else:
        print(f"not found")
    return setArr

print(custom_set(arr, x))