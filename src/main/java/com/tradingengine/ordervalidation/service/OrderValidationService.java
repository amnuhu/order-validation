package com.tradingengine.ordervalidation.service;

import com.tradingengine.ordervalidation.dto.OrderRequestDto;
import com.tradingengine.ordervalidation.dto.redis.RedisOrderDto;
import com.tradingengine.ordervalidation.exceptions.validation.*;

import java.io.IOException;
import java.util.UUID;

public interface OrderValidationService {
    boolean validateSellMarketOrder(RedisOrderDto order, UUID userId) throws IOException, SellLimitException, NoStockException;
    boolean validateSellLimitOrder(RedisOrderDto order, UUID userId) throws IOException, NoStockException, SellLimitException, SellPriceException;
    boolean validateBuyOrderWithMarket(RedisOrderDto order, UUID userId) throws IOException, BuyLimitException, BuyPriceException, NoWalletException;
    boolean validateBuyOrderWithLimit(RedisOrderDto order, UUID userId) throws IOException, BuyPriceException, InsufficientFundsException, BuyLimitException;

}
