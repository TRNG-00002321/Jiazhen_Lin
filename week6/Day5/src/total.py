def read(fileName):
    with open(fileName) as f:
        lines = f.readlines()
        return [int(line) for line in lines]


def calculate_total(fileName):
    num_arr = read(fileName)
    return sum(num_arr)