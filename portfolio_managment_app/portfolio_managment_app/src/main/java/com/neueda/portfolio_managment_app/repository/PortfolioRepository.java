package com.neueda.portfolio_managment_app.repository;

import com.neueda.portfolio_managment_app.entity.PortfolioEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PortfolioRepository extends JpaRepository<PortfolioEntity, Long> {
}
