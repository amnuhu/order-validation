package com.tradingengine.ordervalidation.controller;


import com.tradingengine.ordervalidation.dto.redis.RedisOrderDto;
import com.tradingengine.ordervalidation.events.publisher.Publisher;
import com.tradingengine.ordervalidation.exceptions.validation.*;
import jakarta.servlet.http.HttpServletRequest;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.util.UUID;

@RestController
@RequestMapping("/validation/{portfolioId}")
@Slf4j
public class ValidationController {
    @Autowired
    Publisher publisher;

    @PostMapping
    public void  validateOrder(@RequestBody RedisOrderDto message,
                               @PathVariable UUID portfolioId, HttpServletRequest request) throws BuyPriceException, BuyLimitException, NoWalletException,
            NoStockException, SellPriceException, OrderNotValidException, InsufficientFundsException, IOException, SellLimitException {
        log.info("Validating User Order");
        message.setUserId(UUID.fromString("f1cbc35d-6ca8-4ff7-bfa3-d9038a80e75c"));
        message.setPortfolioId(UUID.fromString("541aa440-5269-4c78-9ef3-39e9b67cf8ca"));
        publisher.publishValidationSuccess(message);

    }

}
