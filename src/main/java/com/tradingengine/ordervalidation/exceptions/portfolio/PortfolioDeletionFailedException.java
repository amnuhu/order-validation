package com.tradingengine.ordervalidation.exceptions.portfolio;

public class PortfolioDeletionFailedException extends Exception{
    public PortfolioDeletionFailedException() {
        super("Portfolio contains stocks");
    }
}
