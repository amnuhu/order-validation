package com.tradingengine.ordervalidation.service.impl;

import com.tradingengine.ordervalidation.entity.PortfolioEntity;
import com.tradingengine.ordervalidation.entity.StockEntity;
import com.tradingengine.ordervalidation.repository.StockRepository;
import com.tradingengine.ordervalidation.service.StockService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Optional;


@Service
@RequiredArgsConstructor
public class StockServiceImpl implements StockService {

    private final StockRepository stockRepository;

    @Override
    public StockEntity fetchStockByPortfolioAndTicker(PortfolioEntity portfolio, String ticker) {
        Optional<StockEntity> stock = stockRepository.findStockEntitiesByPortfolioAndTicker(portfolio,ticker);
        return stock.orElse(null);
    }

    @Override
    public void saveStock(StockEntity stock) {
        stockRepository.save(stock);
    }
}
