package com.tradingengine.ordervalidation.service;

import com.tradingengine.ordervalidation.dto.OrderRequestDto;
import com.tradingengine.ordervalidation.exceptions.validation.*;

import java.io.IOException;
import java.util.UUID;

public interface OrderValidationService {
    boolean validateSellMarketOrder(OrderRequestDto order, UUID userId) throws IOException, SellLimitException, NoStockException;
    boolean validateSellLimitOrder(OrderRequestDto order, UUID userId) throws IOException, NoStockException, SellLimitException, SellPriceException;
    boolean validateBuyOrderWithMarket(OrderRequestDto order, UUID userId) throws IOException, BuyLimitException, BuyPriceException, NoWalletException;
    boolean validateBuyOrderWithLimit(OrderRequestDto order, UUID userId) throws IOException, BuyPriceException, InsufficientFundsException, BuyLimitException;
}
