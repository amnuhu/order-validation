package com.tradingengine.ordervalidation.events.model;

import com.tradingengine.ordervalidation.enums.OrderSide;
import com.tradingengine.ordervalidation.enums.OrderType;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.UUID;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class RedisOrderInformation {
    private String product;
    private Integer quantity;
    private Double price;
    private OrderSide side;
    private OrderType type;
    private UUID portfolioId;
    private UUID userId;
}
