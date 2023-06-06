package com.tradingengine.ordervalidation.dto;


import com.tradingengine.ordervalidation.enums.OrderSide;
import com.tradingengine.ordervalidation.enums.OrderType;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;

import java.util.UUID;

@Setter
@Getter
public class OrderRequestDto{

    private String product;

    private Integer quantity;

    private Double price;

    private OrderSide side;

    private OrderType type;

    private UUID portfolioId;
}

