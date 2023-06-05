package com.tradingengine.ordervalidation.dto;
import jakarta.validation.constraints.NotEmpty;

public record PortfolioRequestDto(
        @NotEmpty
        String name
) {}

