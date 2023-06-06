package com.tradingengine.ordervalidation.dto.redis;


import com.tradingengine.ordervalidation.enums.OrderSide;
import com.tradingengine.ordervalidation.enums.OrderType;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.UUID;

@AllArgsConstructor
@Data
@NoArgsConstructor
public class RedisOrderDto {

    private String product;
    private Integer quantity;
    private Double price;
    private OrderSide side;
    private OrderType type;
    private UUID portfolioId;
    private UUID userId;

}
