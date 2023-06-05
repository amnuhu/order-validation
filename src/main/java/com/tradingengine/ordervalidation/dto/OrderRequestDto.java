package com.tradingengine.ordervalidation.dto;


import com.tradingengine.ordervalidation.enums.OrderSide;
import com.tradingengine.ordervalidation.enums.OrderType;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class OrderRequestDto{
    @NotNull
    private String product;
    @NotNull
    @Min(value = 1)
    private Integer quantity;

    private Double price;
    @NotNull
    private OrderSide side;
    @NotNull
    private OrderType type;
}

