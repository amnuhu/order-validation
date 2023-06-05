package com.tradingengine.ordervalidation.dto;


import com.tradingengine.ordervalidation.entity.PortfolioEntity;

public record StockRequestDto(
        String ticker,
        Integer quantity,
        Double price,
        PortfolioEntity portfolio
) {
}


