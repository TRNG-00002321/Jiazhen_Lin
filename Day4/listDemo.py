from functools import reduce
from itertools import accumulate

arr = [1, 2, 3, 4, 5]

numSquare = list(map(lambda x: x**2, arr))
print(numSquare)

arr = [i for i in range(1, 10)]
numEven = list(filter(lambda x: x%2 == 0, arr))
print(numEven)

arr = [1, 2, 3, 4, 5]
product = reduce(lambda x, y: x*y, arr)
print(product)

numSum = reduce(lambda x, y: x+y, arr)
print(numSum)

name = ["A", "B", "C", "D"]
age = [4, 5, 6, 7]

combined = list(zip(name, age))
print(combined)

combined1 = list(zip(name, age[:3]))
print(combined1)

unzippedNames, unzippedAges = zip(*combined)
print(unzippedNames)
print(unzippedAges)
