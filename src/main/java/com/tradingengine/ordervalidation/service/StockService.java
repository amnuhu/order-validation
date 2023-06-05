package com.tradingengine.ordervalidation.service;



import com.tradingengine.ordervalidation.entity.PortfolioEntity;
import com.tradingengine.ordervalidation.entity.StockEntity;

import java.util.List;

public interface StockService {
    StockEntity fetchStockByPortfolioAndTicker(PortfolioEntity portfolio, String ticker);

    void saveStock(StockEntity stock);

}

