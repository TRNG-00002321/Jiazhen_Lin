package com.revature.bank;
class BankManager {
    public static void main(String[] args) {
        CheckingAccount ca = new CheckingAccount(0, "firstAccount", 0.00);
        SavingAccount sa = new SavingAccount(0, "secondAccount", 0.00);
        System.out.println(ca);
        System.out.println(sa);

        System.out.println(ca.deposit(10000));
        System.out.println(sa.deposit(10000));

        System.out.println(ca.withdraw(5000));
        System.out.println(sa.withdraw(4000));

        System.out.println(ca.getBalance());
        System.out.println(sa.getBalance());

        System.out.println(sa.calculateInterest(sa.interestRate));
        System.out.println(sa.getBalance());

        try {
            ca.deposit(-10000);
        }
        catch (IllegealAmount e) {
            System.out.println(e.getMessage());
        }
    }


}
