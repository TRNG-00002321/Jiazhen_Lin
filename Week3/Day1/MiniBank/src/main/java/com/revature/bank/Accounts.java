package com.revature.bank;


abstract public class Accounts {
    int  id;
    String name;
    double balance;

    public Accounts(int id, String name, double balance) {
        this.id = id;
        this.name = name;
        this.balance = balance;
    }

    @Override
    public String toString() {
        return getClass().getSimpleName() + "{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", balance=" + balance +
                '}';
    }

    public double deposit(double amount) throws IllegealAmount {
        if (amount < 0){
            throw new IllegealAmount("Amount cannot be negative");
        }
        this.balance += amount;
        return amount;
    }

    public double withdraw(double amount) throws IllegealAmount{
        if (amount < 0){
            throw new IllegealAmount("Amount cannot be negative");
        }
        this.balance -= amount;
        return amount;
    };


    public double getBalance(){
        return this.balance;
    }

}

