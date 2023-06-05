package com.tradingengine.ordervalidation.dto.redis;

import lombok.Getter;
import lombok.Setter;

import java.util.UUID;

@Getter
@Setter
public class ClientDetailsDto {
    private UUID portfolioId;
    private UUID userId;
}
