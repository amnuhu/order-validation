package com.tradingengine.ordervalidation.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.UUID;

@Data
@Builder
@Entity
@AllArgsConstructor
@NoArgsConstructor
public class WalletEntity {
    @Id
    @GeneratedValue
    private UUID id;
    private UUID  clientId;
    private double amount;

}

