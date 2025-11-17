package com.revature.bank;
public class SavingAccount extends Accounts implements SimpleInterest{
    double interestRate = 0.5;
    double interest = 0;
    public SavingAccount(int id, String name, double balance) {
        super(id, name, balance);
    }

    @Override
    public double calculateInterest(double percentage) {
        return this.balance += this.balance*(percentage/100);
    }


}
