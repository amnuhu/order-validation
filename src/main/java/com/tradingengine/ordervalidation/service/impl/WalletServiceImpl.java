package com.tradingengine.ordervalidation.service.impl;


import com.tradingengine.ordervalidation.entity.WalletEntity;
import com.tradingengine.ordervalidation.exceptions.wallet.WalletAlreadyCreatedException;
import com.tradingengine.ordervalidation.repository.WalletRepository;
import com.tradingengine.ordervalidation.service.WalletService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;
import java.util.UUID;

@Service
public class WalletServiceImpl implements WalletService {
    @Autowired
    private WalletRepository walletRepository;
    @Override
    public Optional<WalletEntity> getWalletByUserId(UUID userId) {
        return walletRepository.findByClientId(userId);
    }

    @Override
    public void createWallet(UUID userId) throws WalletAlreadyCreatedException {
        // check if userId has a wallet before creating one
        boolean isWalletPresent = walletRepository.findByClientId(userId).isPresent();
        if (isWalletPresent) {
            throw new WalletAlreadyCreatedException("User already has a wallet created");
        }
        WalletEntity walletEntity = WalletEntity.builder()
                .amount(100)
                .clientId(userId)
                .build();
        walletRepository.save(walletEntity);
    }
}
