package com.revature.ex;

public class ThrowDemo {
    public static void validateAge(int age) throws CustomExceptionDemo {
        if (age<18){
            throw new CustomExceptionDemo("Age must be greater than 18. Age is " + age);
        }
        System.out.println("You can vote " + age);
    }

    public static void main(String[] args) {
        int age = 17;
        try{
            validateAge(age);
        }
        catch (CustomExceptionDemo e){
            System.out.println(e.getMessage());
        }
    }
}
