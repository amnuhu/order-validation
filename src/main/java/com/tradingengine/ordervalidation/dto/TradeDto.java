package com.tradingengine.ordervalidation.dto;

import lombok.*;


@Data
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public  class TradeDto {

    private  String product;
    private  int quantity;
    private  double price;
    private  String side;
    private  String orderType;
    private  String exchangeUrl;

}

