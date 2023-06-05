package com.tradingengine.ordervalidation.service.impl;

import com.tradingengine.ordervalidation.dto.TradeDto;
import com.tradingengine.ordervalidation.dto.TradeInfoDto;
import com.tradingengine.ordervalidation.service.MarketDataService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.List;
import java.util.stream.Stream;

@Service
@RequiredArgsConstructor
public class MarketDataServiceImpl implements MarketDataService {
    private final ElasticSearchQueryImpl elasticSearchQuery;
    @Override
    public Stream<TradeInfoDto> getProductByTicker(String ticker) throws IOException {
        return elasticSearchQuery.findProductByTicker(ticker);
    }

    @Override
    public Stream<TradeDto> findOpenTrades(String product, String side) throws IOException {
        return null;
    }

    @Override
    public Stream<TradeDto> findOpenTrades(String product, String side, String orderType, String exchangeOne) throws IOException {
        return null;
    }

    @Override
    public Stream<TradeDto> findOpenTrades(String product, String side, String orderType) throws IOException {
        return null;
    }

    @Override
    public List<TradeDto> findOpenTrades(String product) throws IOException {
        return null;
    }
}
