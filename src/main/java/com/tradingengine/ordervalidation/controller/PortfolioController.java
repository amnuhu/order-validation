package com.tradingengine.ordervalidation.controller;

import com.tradingengine.ordervalidation.dto.PortfolioRequestDto;
import com.tradingengine.ordervalidation.entity.PortfolioEntity;
import com.tradingengine.ordervalidation.exceptions.portfolio.PortfolioDeletionFailedException;
import com.tradingengine.ordervalidation.exceptions.portfolio.PortfolioNotFoundException;
import com.tradingengine.ordervalidation.service.PortfolioService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping(path = "/api/order/portfolio")
@RequiredArgsConstructor
public class PortfolioController {

    private final PortfolioService portfolioService;


    // create portfolio
    @PostMapping
    public ResponseEntity<PortfolioEntity> createPortfolio(@RequestBody
            @Validated PortfolioRequestDto portfolioRequestDto) {
        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(portfolioService.createPortfolio(portfolioRequestDto));
    }
        // fetch all portfolios
    @GetMapping
    public ResponseEntity<List<PortfolioEntity>> fetchAllPortfolios() {
        return ResponseEntity.ok(portfolioService.fetchAllPortfolios());
    }

    // fetch portfolio by portfolioId
    @GetMapping("/{portfolioId}")
    public ResponseEntity<PortfolioEntity> fetchPortfolioById(@PathVariable("portfolioId") UUID portfolioId) throws PortfolioNotFoundException {
            return ResponseEntity
                    .ok()
                    .body(portfolioService.fetchPortfolioById(portfolioId));
        }

    // update portfolio
    @PatchMapping("/{portfolioId}")
    public ResponseEntity<PortfolioEntity> updatePortfolio(@PathVariable("portfolioId") UUID portfolioId,
            @RequestBody @Validated PortfolioRequestDto portfolioRequestDto) throws PortfolioNotFoundException {
        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(portfolioService.updatePortfolio(portfolioId, portfolioRequestDto));
    }

        // delete portfolio
    @DeleteMapping("/{portfolioId}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deletePortfolio(@PathVariable("portfolioId") UUID portfolioId) throws PortfolioNotFoundException, PortfolioDeletionFailedException {
            portfolioService.deletePortfolio(portfolioId);
        }
    }

