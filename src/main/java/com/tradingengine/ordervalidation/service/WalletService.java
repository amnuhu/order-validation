package com.tradingengine.ordervalidation.service;



import com.tradingengine.ordervalidation.entity.WalletEntity;
import com.tradingengine.ordervalidation.exceptions.wallet.WalletAlreadyCreatedException;

import java.util.Optional;
import java.util.UUID;

public interface WalletService {
    Optional<WalletEntity> getWalletByUserId(UUID userId);
    void createWallet(UUID userId) throws WalletAlreadyCreatedException;

}

