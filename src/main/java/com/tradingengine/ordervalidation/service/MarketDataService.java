package com.tradingengine.ordervalidation.service;

import com.tradingengine.ordervalidation.dto.TradeDto;
import com.tradingengine.ordervalidation.dto.TradeInfoDto;

import java.io.IOException;
import java.util.List;
import java.util.stream.Stream;

public interface MarketDataService {
    Stream<TradeInfoDto> getProductByTicker(String ticker) throws IOException;
    Stream<TradeDto> findOpenTrades(String product, String side) throws IOException;
    Stream<TradeDto> findOpenTrades(String product, String side, String orderType, String exchangeOne) throws IOException;
    Stream<TradeDto> findOpenTrades(String product, String side, String orderType) throws IOException;
    List<TradeDto> findOpenTrades(String product) throws IOException;

}


