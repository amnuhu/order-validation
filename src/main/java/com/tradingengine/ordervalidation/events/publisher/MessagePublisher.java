package com.tradingengine.ordervalidation.events.publisher;

import com.tradingengine.ordervalidation.dto.redis.ClientDetailsDto;
import com.tradingengine.ordervalidation.dto.redis.RedisOrderDto;
import com.tradingengine.ordervalidation.events.model.RedisOrderInformation;
import com.tradingengine.ordervalidation.exceptions.validation.*;

import java.io.IOException;

public interface MessagePublisher {

     void publishValidationSuccess(RedisOrderDto order) throws BuyPriceException, BuyLimitException,
             InsufficientFundsException, IOException, NoWalletException, NoStockException, SellPriceException, SellLimitException, OrderNotValidException;
}
