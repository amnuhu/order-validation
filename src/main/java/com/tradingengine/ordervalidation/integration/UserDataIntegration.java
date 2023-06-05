package com.tradingengine.ordervalidation.integration;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;


@RequiredArgsConstructor
@Slf4j
@Service
public class UserDataIntegration {
    private final WebClient webClient;


}
