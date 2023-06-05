package com.tradingengine.ordervalidation.service;


import com.tradingengine.ordervalidation.dto.PortfolioRequestDto;
import com.tradingengine.ordervalidation.entity.PortfolioEntity;
import com.tradingengine.ordervalidation.exceptions.portfolio.PortfolioDeletionFailedException;
import com.tradingengine.ordervalidation.exceptions.portfolio.PortfolioNotFoundException;

import java.util.List;
import java.util.UUID;

public interface PortfolioService {

    PortfolioEntity createPortfolio(PortfolioRequestDto portfolioRequestDto);

    List<PortfolioEntity> fetchAllPortfolios();

    PortfolioEntity fetchPortfolioById(UUID portfolioId) throws PortfolioNotFoundException;

    PortfolioEntity updatePortfolio(UUID portfolioId,PortfolioRequestDto portfolioRequestDto) throws PortfolioNotFoundException;

    void deletePortfolio(UUID portfolioId) throws PortfolioNotFoundException, PortfolioDeletionFailedException;

    PortfolioEntity getPortfolioById(UUID portfolioId) throws PortfolioNotFoundException;
}
