package com.tradingengine.ordervalidation.service.impl;

import com.tradingengine.ordervalidation.dto.PortfolioRequestDto;
import com.tradingengine.ordervalidation.entity.PortfolioEntity;
import com.tradingengine.ordervalidation.exceptions.portfolio.PortfolioNotFoundException;
import com.tradingengine.ordervalidation.repository.PortfolioRepository;
import com.tradingengine.ordervalidation.service.PortfolioService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class PortfolioServiceImpl implements PortfolioService {

    private final PortfolioRepository portfolioRepository;

    public PortfolioEntity createPortfolio(PortfolioRequestDto portfolioRequestDto) {
        PortfolioEntity portfolio = PortfolioEntity.builder()
                .name(portfolioRequestDto.name()).clientId(UUID.randomUUID())
                .build();
        return portfolioRepository.save(portfolio);
    }

    public List<PortfolioEntity> fetchAllPortfolios() {
        return portfolioRepository.findAll();
    }

    public PortfolioEntity fetchPortfolioById(UUID portfolioId) throws PortfolioNotFoundException {
        Optional<PortfolioEntity> portfolio = portfolioRepository.findById(portfolioId);
        if (portfolio.isEmpty()) {
            throw new PortfolioNotFoundException(portfolioId);
        }
        return portfolio.get();
    }

    public PortfolioEntity updatePortfolio(
            UUID portfolioId,
            PortfolioRequestDto portfolioRequestDto
    ) throws PortfolioNotFoundException {
        Optional<PortfolioEntity> portfolio = portfolioRepository.findById(portfolioId);
        if (portfolio.isEmpty()) {
            throw new PortfolioNotFoundException(portfolioId);
        }
        portfolio.get().setName(portfolioRequestDto.name());
        return portfolioRepository.save(portfolio.get());
    }

    public void deletePortfolio(
            UUID portfolioId
    ) throws PortfolioNotFoundException {
        Optional<PortfolioEntity> portfolio = portfolioRepository.findById(portfolioId);

        if (portfolio.isEmpty()) {
            // throw exception
            throw new PortfolioNotFoundException(portfolioId);
        }
        portfolioRepository.deleteById(portfolioId);
    }

    @Override
    public PortfolioEntity getPortfolioById(UUID portfolioId) throws PortfolioNotFoundException {
        return portfolioRepository.findById(portfolioId)
                .orElseThrow(() -> new PortfolioNotFoundException(portfolioId));
    }
}


