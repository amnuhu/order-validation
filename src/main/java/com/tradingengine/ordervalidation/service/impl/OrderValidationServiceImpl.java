package com.tradingengine.ordervalidation.service.impl;

import com.tradingengine.ordervalidation.dto.OrderRequestDto;
import com.tradingengine.ordervalidation.dto.TradeInfoDto;
import com.tradingengine.ordervalidation.entity.StockEntity;
import com.tradingengine.ordervalidation.entity.WalletEntity;
import com.tradingengine.ordervalidation.exceptions.validation.*;
import com.tradingengine.ordervalidation.repository.StockRepository;
import com.tradingengine.ordervalidation.service.MarketDataService;
import com.tradingengine.ordervalidation.service.OrderValidationService;
import com.tradingengine.ordervalidation.service.WalletService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import java.io.IOException;
import java.util.Optional;
import java.util.UUID;
import java.util.stream.Stream;

@Slf4j
@RequiredArgsConstructor
public class OrderValidationServiceImpl implements OrderValidationService {

    private final MarketDataService marketDataService;

    private final WalletService walletService;

    private final StockRepository stockRepository;

    private boolean isQuotedPriceValid(OrderRequestDto order) throws IOException, BuyPriceException {
        log.info("Checking whether your bid price is valid! Not too low or high");
        Stream<TradeInfoDto> products = marketDataService.getProductByTicker(order.getProduct()) ;
        // make sure client's price is greater or equal to the minimum bid price ie bidPrice-maxPriceShift
        // make sure client's price is less than  or equal to the maximum bid price ie bidPrice + maxPriceShift
        boolean isQuotedPriceValid = products.anyMatch(product -> order.getPrice() >=  product.bidPrice() - product.maxPriceShift()
                && order.getPrice() <= product.bidPrice() + product.maxPriceShift());
        if (isQuotedPriceValid) {
            log.info("Your BUY price is valid and may be matched on the Exchange");
            return true;
        }
        log.info("Your BUY price is not Reasonable");
        throw new BuyPriceException("Buy price is too high or too low check market Info");
    }

    private boolean isWalletAmountSufficient(OrderRequestDto order, UUID userId) throws InsufficientFundsException {
        log.info("Checking whether Wallet Balance is sufficient");
        // check if yor wallet balance can match your bidPrice walletAccount >= Order.Price
        boolean isClientWalletSufficient = walletService.getWalletByUserId(userId)
                .stream().anyMatch(wallet -> wallet.getAmount() >= order.getPrice());
        if (isClientWalletSufficient) {
            log.info("Wallet balance is sufficient");
            return true;
        } log.info("Insufficient funds to make a Buy Order");
        throw new InsufficientFundsException("Wallet Balance not Sufficient to make an Order");
    }

    private Boolean isBuyQuantityValid(OrderRequestDto order) throws IOException, BuyLimitException {
        log.info("Validating Buy Limit Order against the current Market Data BuyLimit!");
        Stream<TradeInfoDto> products =  marketDataService.getProductByTicker(order.getProduct());
        log.info("Current Market Buy Limit");
        // check if the quantity you want to buy is available
        boolean verified = products.anyMatch(tradeInfo -> tradeInfo.buyLimit() >= order.getQuantity());
        if (verified) {
            log.info("The quantity you want to buy is valid and  may likely be possible");
            return true;
        };
        log.info("Yor Buy quantity can't be matched");
        throw new BuyLimitException("Quantity not Available on Exchange");
    }

    private double getWalletBalance(UUID userId) throws NoWalletException {
        log.info("Obtaining amount in wallet");
        Optional<WalletEntity> wallet = walletService.getWalletByUserId(userId);
        if (wallet.isPresent()) {
            return wallet.get().getAmount();
        }
        log.info("No wallet found for User");
        throw new NoWalletException("No wallet found");
    }

    // No price validation
    public boolean isWalletBalanceSufficientForMarketOrder(OrderRequestDto order, UUID userId) throws IOException,
            NoWalletException, BuyPriceException {
    double walletBalance = getWalletBalance(userId);
    // since it's a MARKET ORDER you are going all in based on your account balance
    order.setPrice(walletBalance);
    log.info("Market data based on Product Ticker");
    marketDataService.getProductByTicker(order.getProduct()).forEach(System.out::println);
    Stream<TradeInfoDto> products =  marketDataService.getProductByTicker(order.getProduct());
    boolean isQuotedPriceValid = products.anyMatch(product -> order.getPrice() >= product.bidPrice() - product.maxPriceShift());
    if (isQuotedPriceValid) {
        log.info("Your MARKET order has chance of being fulfilled, Account Balance is sufficient for {} Stock", order.getProduct());
        return true;
    }
    log.info("Your MARKET account balanced can't be matched on Exchange");
    throw new BuyPriceException("Your Account balance can't buy this product");
    }

    @Override
    public boolean validateBuyOrderWithMarket(OrderRequestDto order, UUID userId) throws IOException, BuyLimitException, BuyPriceException, NoWalletException {
        // Check the quantity is available and check the wallet has sufficient amount
        log.info("Validating a BUY order with MARKET");
        Stream<TradeInfoDto> products =  marketDataService.getProductByTicker(order.getProduct());
        log.info("Current Market data for {}", order.getProduct());
        products.forEach(System.out::println);
        return  isBuyQuantityValid(order) && isWalletBalanceSufficientForMarketOrder(order, userId);
    }

    @Override
    public boolean validateBuyOrderWithLimit(OrderRequestDto order, UUID userId) throws IOException, BuyPriceException, InsufficientFundsException, BuyLimitException {
        log.info("Validating a BUY order with LIMIT");
        Stream<TradeInfoDto> products =  marketDataService.getProductByTicker(order.getProduct());
        log.info("Current Market data for {}", order.getProduct());
        products.forEach(System.out::println);
        return isQuotedPriceValid(order) && isWalletAmountSufficient(order, userId) && isBuyQuantityValid(order);
    }


    private boolean canUserSellStock(UUID portfolioId, OrderRequestDto order) throws NoStockException, SellLimitException {
        log.info("Validating Sell LIMIT Order By checking if client Owns That Stock");
        StockEntity stock = getUserStockByTickerAndPortfolio(portfolioId, order.getProduct());
        if  (canUserSellStockQuantity(stock, order.getQuantity())) {
            return true;
        }
        log.info("You don't have such quantity to sell, reduce the quantity");
        throw new SellLimitException("You stock doesn't have such quantity to sell");

    }

    private StockEntity getUserStockByTickerAndPortfolio(UUID portfolioId, String ticker) throws NoStockException {
        log.info("Obtaining stock from client portfolio");
        Optional<StockEntity> stock = stockRepository.findStockEntityByPortfolio_ClientIdAndTicker(portfolioId, ticker);
        if (stock.isPresent()) {
            return stock.get();
        }
        log.info("You don't have such stock");
        throw new NoStockException("You don't have such stock");

    }

    private boolean canUserSellStockQuantity(StockEntity stock, int orderedQuantity) {
        return stock.getQuantity() >= orderedQuantity;
    }

    private boolean isSellPriceLimitOrderValid(OrderRequestDto order) throws IOException, SellPriceException {
        Stream<TradeInfoDto> products =  marketDataService.getProductByTicker(order.getProduct());
        boolean isSellPriceValid = products.anyMatch(product ->  order.getPrice() >= product.askPrice() - product.maxPriceShift()
                && order.getPrice() <= product.askPrice() + product.maxPriceShift());
        if (isSellPriceValid) {
            log.info("Your sell price is valid");
            return true;
        }
        log.info("Your sell price is not valid on Exchange");
        throw new SellPriceException("Sell price not valid get Market Data");
    }



    private Boolean validateSellLimit(OrderRequestDto order) throws IOException, SellLimitException {
        log.info("Validating Sell Limit Order against the current Market Data Sell Limit!");
        Stream<TradeInfoDto> products =  marketDataService.getProductByTicker(order.getProduct());
        log.info("Current Market Sell limit");
        boolean verified = products.anyMatch(tradeInfo -> tradeInfo.sellLimit() >= order.getQuantity());
        if (verified) {
            log.info("Sell Quantity is valid");
            return true;
        }
        log.info("You can't sell your stock at that threshold");
        throw new SellLimitException("The quantity you want to sell is not valid on the Exchange");
    }

    @Override
    public  boolean validateSellMarketOrder(OrderRequestDto order, UUID userId) throws IOException, SellLimitException, NoStockException {
        // Selling at Market Order means you will take any price that comes
        if (canUserSellStock(userId, order)) {
            log.info("Current Market data: ");
            marketDataService.getProductByTicker(order.getProduct()).forEach(System.out::println);
            if (validateSellLimit(order)) {
                return true;
            }
        } log.info("You don't have such stock");
        throw new NoStockException("You don't have such Stock in your portfolio");
    }

    @Override
    public boolean validateSellLimitOrder(OrderRequestDto order, UUID userId) throws IOException, NoStockException, SellLimitException, SellPriceException {
        // Selling: check whether client owns the stock
        if (canUserSellStock(userId, order)) {
            log.info("Current Market data: ");
            marketDataService.getProductByTicker(order.getProduct()).forEach(System.out::println);
            if (isSellPriceLimitOrderValid(order)) {
                log.info("Sell Price is possible, Stock can be bought since it's between the range");
                return validateSellLimit(order);
            }
        } log.info("You don't have such stock");
        throw new NoStockException("You don't have such stock in your portfolio");

    }

}
