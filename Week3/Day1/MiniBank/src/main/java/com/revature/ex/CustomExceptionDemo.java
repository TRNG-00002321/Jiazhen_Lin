package com.revature.ex;

//extend what ever exception type
public class CustomExceptionDemo extends Exception {
    public CustomExceptionDemo(String message) {
        super(message);
    }

    @Override
    public String getMessage() {
        return super.getMessage();
    }
}

/*
Try-with-resources
try(some resource){
}
catch

will close the resource without exceptionally closing it or using finally

final - used on variable
finally - used with try block to perform an action
finalize - a method that executes before garbage collection
 */
