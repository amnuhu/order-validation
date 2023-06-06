package com.tradingengine.ordervalidation.events.publisher;

import com.tradingengine.ordervalidation.dto.redis.RedisOrderDto;
import com.tradingengine.ordervalidation.enums.OrderSide;
import com.tradingengine.ordervalidation.enums.OrderType;
import com.tradingengine.ordervalidation.events.model.RedisOrderInformation;
import com.tradingengine.ordervalidation.exceptions.validation.*;
import com.tradingengine.ordervalidation.service.OrderValidationService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.listener.ChannelTopic;
import org.springframework.stereotype.Component;

import java.io.IOException;


@Component
@RequiredArgsConstructor
@Slf4j
public class Publisher implements MessagePublisher {

    @Autowired
    private RedisTemplate<String, Object> redisTemplate;

    @Autowired
    private OrderValidationService validationService;

    @Autowired
    private ChannelTopic topic;




    private void publisher(RedisOrderInformation order) {
        redisTemplate.convertAndSend(topic.getTopic(), order);
        log.info("Messaged received by Publisher, Reading message {}",order);

    }

    @Override
    public void publishValidationSuccess(RedisOrderDto order) throws BuyPriceException, BuyLimitException,
            InsufficientFundsException, IOException, NoWalletException, NoStockException, SellPriceException, SellLimitException, OrderNotValidException {
        log.debug("Sending Redis Message {} for Client Id: {}", order, order.getUserId());
        System.out.println(order);
        OrderSide orderSide = order.getSide();
        switch (orderSide) {
            case BUY -> {
                OrderType orderType = order.getType();
                switch (orderType) {
                    case LIMIT -> {
                        if (validationService.validateBuyOrderWithLimit(order, order.getUserId())){
                            publisher(createOrderInformation(order));
                        }
                    }
                    case MARKET -> {
                        if (validationService.validateBuyOrderWithMarket(order, order.getUserId())) {
                            publisher(createOrderInformation(order));
                        };
                    }
                }
            }
            case SELL -> {
                OrderType orderType = order.getType();
                switch (orderType) {
                    case LIMIT -> {
                        if (validationService.validateSellLimitOrder(order, order.getUserId())) {
                            publisher(createOrderInformation(order));
                        };
                    }
                    case MARKET -> {
                        if (validationService.validateSellMarketOrder(order, order.getUserId())) {
                            publisher(createOrderInformation(order));
                        };
                    }
                }
            }

        }
    }


    private RedisOrderInformation createOrderInformation(RedisOrderDto order) {
        return RedisOrderInformation.builder()
                .product(order.getProduct())
                .price(order.getPrice())
                .quantity(order.getQuantity())
                .side(order.getSide())
                .type(order.getType())
                .portfolioId(order.getPortfolioId())
                .userId(order.getUserId()).build();
    }

}
