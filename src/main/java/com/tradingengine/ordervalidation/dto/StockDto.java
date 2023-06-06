package com.tradingengine.ordervalidation.dto;

import jakarta.persistence.*;
import lombok.*;


@Setter
@Getter
public class StockDto {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long stockId;
    private String ticker;
    private Integer quantity;
    private Double price;

}




