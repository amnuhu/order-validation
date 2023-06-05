package com.tradingengine.ordervalidation.repository;


import com.tradingengine.ordervalidation.entity.PortfolioEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;
import java.util.UUID;

@Repository
public interface PortfolioRepository extends JpaRepository<PortfolioEntity, UUID> {

}
