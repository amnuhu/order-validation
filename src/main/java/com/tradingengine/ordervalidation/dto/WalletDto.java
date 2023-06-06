package com.tradingengine.ordervalidation.dto;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import lombok.*;

import java.util.UUID;

@Setter
@Getter
public class WalletDto {
    private UUID  clientId;
    private double amount;

}

