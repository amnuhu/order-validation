package com.tradingengine.ordervalidation.repository;


import com.tradingengine.ordervalidation.entity.WalletEntity;
import org.springframework.data.jpa.repository.JpaRepository;


import java.util.Optional;
import java.util.UUID;


public interface WalletRepository  extends JpaRepository<WalletEntity, UUID> {
    Optional<WalletEntity> findByClientId(UUID uuid);
}

