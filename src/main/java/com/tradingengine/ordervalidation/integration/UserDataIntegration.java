package com.tradingengine.ordervalidation.integration;

import com.tradingengine.ordervalidation.dto.StockDto;
import com.tradingengine.ordervalidation.dto.WalletDto;
import com.tradingengine.ordervalidation.exceptions.validation.NoStockException;
import com.tradingengine.ordervalidation.exceptions.validation.NoWalletException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;

import java.util.UUID;


@RequiredArgsConstructor
@Slf4j
@Service
public class UserDataIntegration {
    private final WebClient webClient;


    public ResponseEntity<WalletDto> getWallet(UUID userId) {
        return webClient.get()
                .uri("http://localhost:8080/wallet/" + userId)
                .retrieve()
                .onStatus(s -> s.equals(HttpStatus.NOT_FOUND), (e) -> Mono.error(new NoWalletException("No wallet found")))
                .toEntity(WalletDto.class)
                .block();
    }

    public ResponseEntity<StockDto> getUserStockByTickerAndPortfolio(UUID portfolioId, String ticker){
        return webClient.get()
                .uri("http://localhost:8080/stock/" + portfolioId + "/" + ticker)
                .retrieve()
                .onStatus(s -> s.equals(HttpStatus.NOT_FOUND), (e) -> Mono.error(new NoStockException("No stock found")))
                .toEntity(StockDto.class)
                .block();

    }


}
