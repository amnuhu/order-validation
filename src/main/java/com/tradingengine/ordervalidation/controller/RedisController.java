package com.tradingengine.ordervalidation.controller;

import com.tradingengine.ordervalidation.dto.redis.RedisOrderDto;
import com.tradingengine.ordervalidation.events.model.RedisOrderInformation;
import com.tradingengine.ordervalidation.events.publisher.Publisher;
import com.tradingengine.ordervalidation.exceptions.validation.*;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.io.IOException;
import java.util.UUID;

@RestController
@Slf4j
public class RedisController {

    @Autowired
    Publisher publisher;

    @RequestMapping(value = "/publisher", method = RequestMethod.POST)
    public void  publish(@RequestBody RedisOrderDto message) throws BuyPriceException, BuyLimitException, NoWalletException,
            NoStockException, SellPriceException, OrderNotValidException, InsufficientFundsException, IOException,
            SellLimitException {
        message.setUserId(UUID.fromString("f1cbc35d-6ca8-4ff7-bfa3-d9038a80e75c"));
        message.setPortfolioId(UUID.fromString("541aa440-5269-4c78-9ef3-39e9b67cf8ca"));
        log.info(">>>> Publishing:   {}", message);
        publisher.publishValidationSuccess(message);
    }
}
