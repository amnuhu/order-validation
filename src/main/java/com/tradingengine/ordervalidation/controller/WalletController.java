package com.tradingengine.ordervalidation.controller;


import com.tradingengine.ordervalidation.entity.WalletEntity;
import com.tradingengine.ordervalidation.exceptions.wallet.WalletAlreadyCreatedException;
import com.tradingengine.ordervalidation.service.WalletService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@RestController
@RequestMapping("/api/wallet")
@RequiredArgsConstructor
public class WalletController {

    private final WalletService walletService;

    @GetMapping("/{userId}")
    public WalletEntity createOrder(@PathVariable("userId") UUID userId) throws WalletAlreadyCreatedException {
        walletService.createWallet(userId);
        return walletService.getWalletByUserId(userId).orElseThrow(RuntimeException::new);
    }

}

