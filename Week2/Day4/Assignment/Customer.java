package Assignment;

import java.util.Random;

public class Customer {
    static int numberOfCustomers;
    int id;
    final int RANDOM_NUMBER;
    //one static and final
    int age;
    Random random = new Random();

    //two constructors and overload methods
    Customer(int n){
        this.id = random.nextInt(n);
        numberOfCustomers += 1;
        this.RANDOM_NUMBER = n;
    }

    Customer(String n){
        this.id = random.nextInt(Integer.parseInt(n));
        numberOfCustomers += 1;
        this.RANDOM_NUMBER = Integer.parseInt(n);
    }

    void setAge(int a){
        this.age = a;
    }

    void setAge(String a){
        this.age = Integer.parseInt(a);
    }
}
