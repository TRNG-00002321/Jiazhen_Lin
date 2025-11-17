package com.revature.bank;
public class CheckingAccount extends Accounts{
    double surgeCharge = 0.01;

    public CheckingAccount(int id, String name, double balance) {
        super(id, name, balance);
    }

    public CheckingAccount(int id, String name, double balance, double surgeCharge) {
        super(id, name, balance);
        this.surgeCharge = surgeCharge;
    }

    public void setSurgeCharge(double surgeCharge) {
        this.surgeCharge = surgeCharge;
    }

    @Override
    public double withdraw(double amount) throws IllegealAmount{
        if (amount < 0){
            throw new IllegealAmount("Amount cannot be negative");
        }
        this.balance -= amount;
        return (amount-amount*surgeCharge/100);
    }
}
