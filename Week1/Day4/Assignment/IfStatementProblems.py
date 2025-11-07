"""
For this problem you are going to make a program that simulates the output of a vending machine that only takes in
coins of American currency.
1.Your program should take an integer as an input from the user (either a 1, 2, or 3) that corresponds with an option
for a drink from the vending machine outlined below and assign the corresponding price to a variable as a float.
Use your knowledge of if, elif, and else statements to complete this part of the problem. Your code should
have an else statement that prints a message and ends the program using sys.exit() if the user does not enter a valid
input number.
Vending Machine:
1.water = $1.00
2.cola = $1.50
3.gatorade = $2.00
2.After placing an order, the user should be prompted to enter inputs 4 times:
1.The first time, the user should be prompted to enter the number of quarters they put in the machine. Assign this
number to a variable as an integer.
2.The second time, the user should be prompted to enter the number of dimes they put in the machine. Assign this
number to a variable as an integer.
3.The third time, the user should be prompted to enter the number of nickles they put in the machine. Assign this
number to a variable as an integer.
4.The fourth time, the user should be prompted to enter the number of pennies they put in the machine. Assign this
number to a variable as an integer.
3.Create a variable to hold the total value of all the coins the user has put into the machine.
4.Use flow control statements to print the user's change or output a message asking the user to try again depending
on whether the total value of the coins the user has put into the machine is enough to pay for the item they ordered.
New knowledge for this problem:
1.%f format specifier
2.import sys and sys.exit()
3.int()
"""
import sys

class VendingMachine:
    def __init__(self, items):
        self.items = items

    def get_user_order(self):
        try:
            order = int(input())
            if order in list(self.items.keys()):
                cost = self.items[order]
                print(f"Cost: ${cost}")
                amount = self.get_coins()
                self.pay(amount, cost)
            else:
                raise ValueError
        except ValueError:
            print("Please enter a valid number")
            sys.exit()

    def get_coins(self):
        q = int(input("Please enter the number of quarters: "))
        d = int(input("Please enter the number of dimes: "))
        n = int(input("Please enter the number of nickles: "))
        p = int(input("Please enter the number of pennies: "))

        values = [.25, .1, .05, .01]
        coins = [q, d, n, p]

        return round(sum([num_coins * value for num_coins, value in zip(coins,values)]),2)

    def pay(self, amount, cost):
        print(f"Total: ${amount}")
        if amount < cost:
            print(f"Insufficient funds")
        else:
            print("Vending order...")
            if amount > cost:
                print(f"Refunding ${amount-cost}")
        sys.exit()

items_list1 = {1 : 1.00, 2 :1.50, 3: 2.00}
vm = VendingMachine(items_list1)
vm.get_user_order()