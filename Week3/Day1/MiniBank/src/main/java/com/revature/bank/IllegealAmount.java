package com.revature.bank;

public class IllegealAmount extends IllegalArgumentException {
    public IllegealAmount(String message) {
        super(message);
    }

    @Override
    public String toString(){
        return super.toString();
    }
}
