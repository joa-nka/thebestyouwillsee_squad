package com.neueda.portfolio_managment_app.controller;

import com.neueda.portfolio_managment_app.entity.PortfolioItemEntity;
import com.neueda.portfolio_managment_app.enumes.Exchange;
import com.neueda.portfolio_managment_app.repository.PortfolioItemRepository;
import com.neueda.portfolio_managment_app.service.FxService;
import com.neueda.portfolio_managment_app.service.ReportService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.math.BigDecimal;
import java.util.*;


@RestController
@RequestMapping("/reports")
public class ReportController {

    private final ReportService reportService;

    public ReportController(ReportService reportService) {
        this.reportService = reportService;
    }

    @GetMapping("/portfolio/{id}/holdings-eur")
    public List<Map<String, Object>> getPortfolioHoldingsInEur(@PathVariable Long id) {
        return reportService.getPortfolioHoldingsInEur(id);
    }

    @GetMapping("/portfolio/{id}/purchases-eur")
    public List<Map<String, Object>> getPurchasesInEur(@PathVariable Long id) {
        return reportService.getPurchasesInEur(id);
    }

    @GetMapping("/portfolio/{id}/current-value")
    public List<Map<String, Object>> getCurrentValue(@PathVariable Long id) {
        return reportService.getCurrentValue(id);
    }

    @GetMapping("/portfolio/{id}/profit-loss")
    public List<Map<String, Object>> getProfitLoss(@PathVariable Long id) {
        return reportService.getProfitLoss(id);
    }
}
