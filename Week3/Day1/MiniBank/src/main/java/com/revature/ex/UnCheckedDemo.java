package com.revature.ex;

import java.io.FileInputStream;
import java.io.FileNotFoundException;

public class UnCheckedDemo {
    public static void main(String[] args) {
        try {
            FileInputStream fis = new FileInputStream("MyNonExistentFile.txt");
        } catch (FileNotFoundException e) {
            throw  new RuntimeException(e);
        } catch (ArithmeticException e) {
            throw  new RuntimeException(e);
        }
        finally {
            System.out.println("Occurs no matter what");
            System.out.println("Usd to clean up things");
        }
    }
}
