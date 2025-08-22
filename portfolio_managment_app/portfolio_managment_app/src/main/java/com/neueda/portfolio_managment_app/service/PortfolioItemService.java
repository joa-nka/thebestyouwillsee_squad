package com.neueda.portfolio_managment_app.service;

import com.neueda.portfolio_managment_app.entity.PortfolioEntity;
import com.neueda.portfolio_managment_app.entity.PortfolioItemEntity;
import com.neueda.portfolio_managment_app.repository.PortfolioItemRepository;
import com.neueda.portfolio_managment_app.repository.PortfolioRepository;
import jakarta.persistence.EntityNotFoundException;
import jakarta.transaction.Transactional;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class PortfolioItemService {

    private final PortfolioRepository portfolios;
    private final PortfolioItemRepository items;

    public PortfolioItemService(PortfolioRepository portfolios, PortfolioItemRepository items) {
        this.portfolios = portfolios; this.items = items; }

    public PortfolioItemEntity addItem(Long portfolioId, String assetCode, int quantity) {
        PortfolioEntity portfolioEntity = portfolios.findById(portfolioId)
                .orElseThrow(() -> new EntityNotFoundException("Portfolio not found: " + portfolioId));

        PortfolioItemEntity portfolioItemEntity = new PortfolioItemEntity();
        portfolioItemEntity.setQuantity(quantity);
        portfolioItemEntity.setAssetCode(assetCode);
        portfolioItemEntity.setPortfolioEntity(portfolioEntity);

        return items.save(portfolioItemEntity);
    }

    public List<PortfolioItemEntity> findByPortfolio(Long portfolioId) {
        return items.findByPortfolioId(portfolioId); }


    public PortfolioItemEntity findOne(Long itemId) {
        return items.findById(itemId).orElseThrow(() -> new EntityNotFoundException("Item not found: " + itemId));
    }

    public PortfolioItemEntity changeQty(Long itemId, int qty) {
        PortfolioItemEntity it = findOne(itemId); it.setQuantity(qty);
        return items.save(it);
    }
    public PortfolioItemEntity changeSymbol(Long itemId, String assetCode) {
        PortfolioItemEntity it = findOne(itemId); it.setAssetCode(assetCode);
        return items.save(it);
    }

    public void delete(Long itemId) { items.deleteById(itemId); }
}
