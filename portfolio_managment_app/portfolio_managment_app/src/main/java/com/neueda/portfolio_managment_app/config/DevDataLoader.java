package com.neueda.portfolio_managment_app.config;

import com.neueda.portfolio_managment_app.entity.PortfolioEntity;
import com.neueda.portfolio_managment_app.entity.PortfolioItemEntity;
import com.neueda.portfolio_managment_app.enumes.Exchange;
import com.neueda.portfolio_managment_app.repository.PortfolioItemRepository;
import com.neueda.portfolio_managment_app.repository.PortfolioRepository;
import com.neueda.portfolio_managment_app.service.PortfolioItemService;
import com.neueda.portfolio_managment_app.service.PortfolioService;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;
import java.time.LocalDate;

@Component
@Profile("dev")
public class DevDataLoader implements CommandLineRunner {

    private final PortfolioService portfolios;
    private final PortfolioItemService items;

    public DevDataLoader(PortfolioService portfolios, PortfolioItemService items) {
        this.portfolios = portfolios;
        this.items = items;
    }

    @Override
    public void run(String... args) {
        boolean exists = portfolios.findAll().stream()
                .anyMatch(p -> "MyGlobalPortfolio".equals(p.getName()));
        if (exists) return;

        PortfolioEntity p = portfolios.create("MyGlobalPortfolio");

        items.addItem(p.getId(), "PKN", 10, new BigDecimal("87.50"),  Exchange.WSE,    LocalDate.now().minusDays(30));
        items.addItem(p.getId(), "PKO", 15, new BigDecimal("41.20"),  Exchange.WSE,    LocalDate.now().minusDays(24));
        items.addItem(p.getId(), "KGH", 12, new BigDecimal("120.00"), Exchange.WSE,    LocalDate.now().minusDays(20));
        items.addItem(p.getId(), "CDR", 8,  new BigDecimal("150.00"), Exchange.WSE,    LocalDate.now().minusDays(16));
        items.addItem(p.getId(), "PZU", 20, new BigDecimal("50.00"),  Exchange.WSE,    LocalDate.now().minusDays(12));

        items.addItem(p.getId(), "AAPL",  5, new BigDecimal("195.30"), Exchange.NASDAQ, LocalDate.now().minusDays(28));
        items.addItem(p.getId(), "MSFT",  4, new BigDecimal("405.10"), Exchange.NASDAQ, LocalDate.now().minusDays(22));
        items.addItem(p.getId(), "NVDA",  2, new BigDecimal("110.00"), Exchange.NASDAQ, LocalDate.now().minusDays(18));
        items.addItem(p.getId(), "AMZN",  3, new BigDecimal("135.75"), Exchange.NASDAQ, LocalDate.now().minusDays(14));
        items.addItem(p.getId(), "GOOGL", 4, new BigDecimal("128.20"), Exchange.NASDAQ, LocalDate.now().minusDays(6));
        items.addItem(p.getId(), "IBM",  3, new BigDecimal("170.00"),  Exchange.NYSE,  LocalDate.now().minusDays(26));
        items.addItem(p.getId(), "KO",  10, new BigDecimal("60.00"),   Exchange.NYSE,  LocalDate.now().minusDays(18));
        items.addItem(p.getId(), "JNJ",  5, new BigDecimal("160.00"),  Exchange.NYSE,  LocalDate.now().minusDays(10));

        items.addItem(p.getId(), "DBK", 12, new BigDecimal("13.20"),   Exchange.XETRA, LocalDate.now().minusDays(25));
        items.addItem(p.getId(), "SAP",  6, new BigDecimal("125.00"),  Exchange.XETRA, LocalDate.now().minusDays(8));
        items.addItem(p.getId(), "BMW",  7, new BigDecimal("92.00"),   Exchange.XETRA, LocalDate.now().minusDays(4));

        items.addItem(p.getId(), "HSBA", 8,  new BigDecimal("6.50"),   Exchange.LSE,   LocalDate.now().minusDays(5));
        items.addItem(p.getId(), "VOD", 20,  new BigDecimal("0.80"),   Exchange.LSE,   LocalDate.now().minusDays(2));
    }



}
