package com.tradingengine.ordervalidation.exceptions.portfolio;

import java.util.UUID;

public class PortfolioNotFoundException extends Exception{
    public PortfolioNotFoundException(UUID portfolioId) {
        super("Portfolio with id: "+portfolioId+" not found");
    }
}


