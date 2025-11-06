randomList = "apple banana cherry lime orange".split()
contains = "a"

print([item for item in randomList if contains in item])

randomList = "apple banana cherry lime orange".split()
contains = "a"
l = [item if contains in item else -1 for item in randomList]
print(l)

