package com.neueda.portfolio_managment_app.service;

import com.neueda.portfolio_managment_app.entity.PortfolioEntity;
import com.neueda.portfolio_managment_app.entity.PortfolioItemEntity;
import com.neueda.portfolio_managment_app.enumes.Exchange;
import com.neueda.portfolio_managment_app.repository.PortfolioItemRepository;
import com.neueda.portfolio_managment_app.repository.PortfolioRepository;
import jakarta.persistence.EntityNotFoundException;
import jakarta.transaction.Transactional;
import org.springframework.data.crossstore.ChangeSetPersister;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;

@Service
public class PortfolioItemService {

    private final PortfolioRepository portfolios;
    private final PortfolioItemRepository items;

    public PortfolioItemService(PortfolioRepository portfolios, PortfolioItemRepository items) {
        this.portfolios = portfolios; this.items = items; }

    public PortfolioItemEntity addItem(Long portfolioId, String assetCode, int quantity, BigDecimal buyPrice, Exchange exchange, LocalDate tradeDate) {
        PortfolioEntity portfolioEntity = portfolios.findById(portfolioId)
                .orElseThrow(() -> new EntityNotFoundException("Portfolio not found: " + portfolioId));

        PortfolioItemEntity portfolioItemEntity = new PortfolioItemEntity();
        portfolioItemEntity.setAssetCode(assetCode);
        portfolioItemEntity.setQuantity(quantity);
        portfolioItemEntity.setBuyPrice(buyPrice);
        portfolioItemEntity.setExchange(exchange);
        portfolioItemEntity.setPortfolioEntity(portfolioEntity);
        portfolioItemEntity.setTradeDate(tradeDate);

        return items.save(portfolioItemEntity);
    }

    public List<PortfolioItemEntity> findByPortfolio(Long portfolioId) {
        return items.findByPortfolioEntityId(portfolioId); }


    public PortfolioItemEntity findOne(Long itemId) {
        return items.findById(itemId).orElseThrow(() -> new EntityNotFoundException("Item not found: " + itemId));
    }

    public PortfolioItemEntity changeQty(Long itemId, int qty) {
        PortfolioItemEntity it = findOne(itemId); it.setQuantity(qty);
        return items.save(it);
    }
    public PortfolioItemEntity changeAssetCode(Long itemId, String assetCode) {
        PortfolioItemEntity it = findOne(itemId); it.setAssetCode(assetCode);
        return items.save(it);
    }

    @Transactional
    public void delete(Long itemId) {
//        PortfolioItemEntity item = items.findBy(itemId)
//                .orElseThrow(() -> new ChangeSetPersister.NotFoundException("Item not found"));
//        PortfolioEntity portfolio = item.getPortfolioEntity();
//        portfolio.getItems().remove(item)
        items.deleteById(itemId); }
}
