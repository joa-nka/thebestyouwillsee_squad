package com.neueda.portfolio_managment_app.service;

import com.neueda.portfolio_managment_app.entity.PortfolioItemEntity;
import com.neueda.portfolio_managment_app.enumes.Exchange;
import com.neueda.portfolio_managment_app.repository.PortfolioItemRepository;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ReportService {
    private final PortfolioItemRepository portfolioItemRepository;
    private final FxService fxService;
    private final PriceServiceCached priceServiceCached;

    public ReportService(PortfolioItemRepository portfolioItemRepository,
                         FxService fxService,
                         PriceServiceCached priceServiceCached) {
        this.portfolioItemRepository = portfolioItemRepository;
        this.fxService = fxService;
        this.priceServiceCached = priceServiceCached;
    }

    public List<Map<String, Object>> getPortfolioHoldingsInEur(Long portfolioId) {
        Map<Exchange, BigDecimal> totals = new HashMap<>();

        for (PortfolioItemEntity item : portfolioItemRepository.findByPortfolioEntityId(portfolioId)) {
            BigDecimal value = item.getBuyPrice()
                    .multiply(BigDecimal.valueOf(item.getQuantity()));
            BigDecimal valueInEur = fxService.convertToBase(value, item.getExchange().getCurrency());

            totals.merge(item.getExchange(), valueInEur, BigDecimal::add);
        }

        List<Map<String, Object>> result = new ArrayList<>();
        for (Map.Entry<Exchange, BigDecimal> entry : totals.entrySet()) {
            Map<String, Object> row = new HashMap<>();
            row.put("exchange", entry.getKey().name());
            row.put("totalEur", entry.getValue());
            result.add(row);
        }

        return result;
    }

    public List<Map<String, Object>> getPurchasesInEur(Long portfolioId) {
        List<Map<String, Object>> out = new ArrayList<>();
        for (PortfolioItemEntity it : portfolioItemRepository.findByPortfolioEntityId(portfolioId)) {
            BigDecimal valueSrc = it.getBuyPrice().multiply(BigDecimal.valueOf(it.getQuantity()));
            BigDecimal valueEur = fxService.convertToBase(valueSrc, it.getExchange().getCurrency());
            out.add(Map.of(
                    "asset", it.getAssetCode(),
                    "exchange", it.getExchange().name(),
                    "valueEur", valueEur
            ));
        }
        return out;
    }

    public List<Map<String, Object>> getCurrentValue(Long portfolioId) {
        List<Map<String, Object>> out = new ArrayList<>();
        for (PortfolioItemEntity it : portfolioItemRepository.findByPortfolioEntityId(portfolioId)) {
            BigDecimal currentPrice = priceServiceCached.getCurrentPrice(it.getAssetCode(), it.getExchange());
            BigDecimal currentValue = currentPrice.multiply(BigDecimal.valueOf(it.getQuantity()));
            BigDecimal valueEur = fxService.convertToBase(currentValue, it.getExchange().getCurrency());
            out.add(Map.of(
                    "asset", it.getAssetCode(),
                    "exchange", it.getExchange().name(),
                    "currentValueEur", valueEur
            ));
        }
        return out;
    }

    public List<Map<String, Object>> getProfitLoss(Long portfolioId) {
        List<Map<String, Object>> out = new ArrayList<>();
        for (PortfolioItemEntity it : portfolioItemRepository.findByPortfolioEntityId(portfolioId)) {
            BigDecimal buyValue = it.getBuyPrice().multiply(BigDecimal.valueOf(it.getQuantity()));
            BigDecimal buyValueEur = fxService.convertToBase(buyValue, it.getExchange().getCurrency());

            BigDecimal currentPrice = priceServiceCached.getCurrentPrice(it.getAssetCode(), it.getExchange());
            BigDecimal currentValue = currentPrice.multiply(BigDecimal.valueOf(it.getQuantity()));
            BigDecimal currentValueEur = fxService.convertToBase(currentValue, it.getExchange().getCurrency());

            BigDecimal profitLoss = currentValueEur.subtract(buyValueEur);

            out.add(Map.of(
                    "asset", it.getAssetCode(),
                    "exchange", it.getExchange().name(),
                    "buyValueEur", buyValueEur,
                    "currentValueEur", currentValueEur,
                    "profitLossEur", profitLoss
            ));
        }
        return out;
    }
}
