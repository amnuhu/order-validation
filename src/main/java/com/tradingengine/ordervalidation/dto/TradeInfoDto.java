package com.tradingengine.ordervalidation.dto;

public record TradeInfoDto(
        String ticker,
        int sellLimit,
        double  lastTradedPrice,
        double maxPriceShift,
        double askPrice,
        double bidPrice,
        int buyLimit,
        String exchangeUrl
){}
