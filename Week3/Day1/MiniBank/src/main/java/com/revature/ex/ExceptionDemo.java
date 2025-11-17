package com.revature.ex;

public class ExceptionDemo {
    public static void main(String[] args) {
        int[] arr = new int[5];
        try{
            arr[5] = 10;
            System.out.println(arr[5]);
        }
        catch(ArrayIndexOutOfBoundsException e){
            System.out.println(e.getMessage());
        }
    }
}
