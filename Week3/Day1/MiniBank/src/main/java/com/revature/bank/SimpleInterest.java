package com.revature.bank;
public interface SimpleInterest {
    public default void calculateSimpleInterest(double percent){
        System.out.println("Please Implement");
    };

    public double calculateInterest(double amount);


    // new default lets you not implement method
    // interface having only one abstract is known as a functional interface
    // and facilitates functional programming by using lambda method
    // single abstract method interface - SAM interface
    // without any abstract methods is marker interface
    //use to mark something as something - ex: serializable and remote

}
