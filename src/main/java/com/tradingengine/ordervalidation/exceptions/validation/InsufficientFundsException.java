package com.tradingengine.ordervalidation.exceptions.validation;

public class InsufficientFundsException extends Exception{
    public InsufficientFundsException(String message) {
        super(message);
    }
}
