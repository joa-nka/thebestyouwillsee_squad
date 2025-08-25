package com.neueda.portfolio_managment_app.controller;

import com.neueda.portfolio_managment_app.entity.PortfolioItemEntity;
import com.neueda.portfolio_managment_app.enumes.Exchange;
import com.neueda.portfolio_managment_app.repository.PortfolioItemRepository;
import com.neueda.portfolio_managment_app.service.FxService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.math.BigDecimal;
import java.util.*;


@RestController
@RequestMapping("/reports")
public class ReportController {

    private final PortfolioItemRepository portfolioItemRepository;
    private final FxService fxService;

    public ReportController(PortfolioItemRepository portfolioItemRepository, FxService fxService) {
        this.portfolioItemRepository = portfolioItemRepository;
        this.fxService = fxService;
    }
    @GetMapping("/portfolio/{id}/holdings-eur")
    public List<Map<String, Object>> getPortfolioHoldingsInEur(@PathVariable Long id) {
        Map<Exchange, BigDecimal> totals = new HashMap<>();

        for (PortfolioItemEntity item : portfolioItemRepository.findByPortfolioEntityId(id)) {
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

    @GetMapping("/portfolio/{id}/purchases-eur")
    public List<Map<String, Object>> getPurchasesInEur(@PathVariable Long id) {
        List<Map<String, Object>> out = new ArrayList<>();
        for (PortfolioItemEntity it : portfolioItemRepository.findByPortfolioEntityId(id)) {
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


}
