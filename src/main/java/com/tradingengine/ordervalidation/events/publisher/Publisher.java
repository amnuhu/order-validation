package com.tradingengine.ordervalidation.events.publisher;


import com.tradingengine.ordervalidation.dto.OrderRequestDto;
import com.tradingengine.ordervalidation.dto.redis.ClientDetailsDto;
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



    @Override
    public void publisher(RedisOrderDto order) {
        redisTemplate.convertAndSend(topic.getTopic(), order);
        log.info("Messaged received by Publisher, Reading message {}",order);

    }

    @Override
    public void publishValidationSuccess(RedisOrderDto orderDto) {

    }

    public RedisOrderInformation publishValidationSuccess(OrderRequestDto orderDto, ClientDetailsDto client) throws BuyPriceException, BuyLimitException,
            InsufficientFundsException, IOException, NoWalletException, NoStockException, SellPriceException, SellLimitException, OrderNotValidException {
        log.debug("Sending Redis Message {} for Client Id: {}", orderDto, client.getUserId());
        OrderSide orderSide = orderDto.getSide();
        switch (orderSide) {
            case BUY -> {
                OrderType orderType = orderDto.getType();
                switch (orderType) {
                    case LIMIT -> {
                        if (validationService.validateBuyOrderWithLimit(orderDto, client.getUserId())){
                            return createOrderInformation(orderDto, client);
                        }
                    }
                    case MARKET -> {
                        if (validationService.validateBuyOrderWithMarket(orderDto, client.getUserId())) {
                            return createOrderInformation(orderDto, client);
                        };
                    }
                }
            }
            case SELL -> {
                OrderType orderType = orderDto.getType();
                switch (orderType) {
                    case LIMIT -> {
                        if (validationService.validateSellLimitOrder(orderDto, client.getUserId())) {
                            return createOrderInformation(orderDto, client);
                        };
                    }
                    case MARKET -> {
                        if (validationService.validateSellMarketOrder(orderDto, client.getUserId())) {
                            return createOrderInformation(orderDto, client);
                        };
                    }
                }
            }

        }
        throw new OrderNotValidException("Order not Valid");
    }


    private RedisOrderInformation createOrderInformation(OrderRequestDto order, ClientDetailsDto client) {
        return RedisOrderInformation.builder()
                .product(order.getProduct())
                .quantity(order.getQuantity())
                .side(order.getSide())
                .type(order.getType())
                .portfolioId(client.getPortfolioId())
                .userId(client.getUserId()).build();
    }

}
