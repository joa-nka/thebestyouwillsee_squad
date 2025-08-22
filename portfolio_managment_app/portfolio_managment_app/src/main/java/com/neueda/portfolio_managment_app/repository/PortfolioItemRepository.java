package com.neueda.portfolio_managment_app.repository;

import com.neueda.portfolio_managment_app.entity.PortfolioItemEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface PortfolioItemRepository extends JpaRepository<PortfolioItemEntity, Long> {

    List<PortfolioItemEntity> findByPortfolioId(Long portfolioId);
}
