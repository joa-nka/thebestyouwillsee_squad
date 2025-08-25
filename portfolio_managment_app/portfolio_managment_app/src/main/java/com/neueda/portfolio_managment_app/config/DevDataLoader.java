package com.neueda.portfolio_managment_app.config;

import com.neueda.portfolio_managment_app.entity.PortfolioEntity;
import com.neueda.portfolio_managment_app.entity.PortfolioItemEntity;
import com.neueda.portfolio_managment_app.repository.PortfolioItemRepository;
import com.neueda.portfolio_managment_app.repository.PortfolioRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Component;

@Component
@Profile("dev")
public class DevDataLoader implements CommandLineRunner {

    private final PortfolioRepository portfolioRepository;
    private final PortfolioItemRepository portfolioItemRepository;

    public DevDataLoader(PortfolioRepository portfolioRepository, PortfolioItemRepository portfolioItemRepository) {
        this.portfolioRepository = portfolioRepository;
        this.portfolioItemRepository = portfolioItemRepository;
    }

    @Override public void run(String... args){
        if (portfolioRepository.count() > 0) return;
        PortfolioEntity portfolio = portfolioRepository.save(new PortfolioEntity("Demo Portfolio"));
        portfolioItemRepository.save(link(new PortfolioItemEntity("AAPL", 5), portfolio));
        portfolioItemRepository.save(link(new PortfolioItemEntity("MSFT", 7), portfolio));
        portfolioItemRepository.save(link(new PortfolioItemEntity("GOOGL", 3), portfolio));
        portfolioItemRepository.save(link(new PortfolioItemEntity("AMZN", 4), portfolio));
        portfolioItemRepository.save(link(new PortfolioItemEntity("TSLA", 2), portfolio));
    }

    private PortfolioItemEntity link(PortfolioItemEntity portfolioItemEntity, PortfolioEntity portfolioEntity){
        portfolioItemEntity.setPortfolioEntity(portfolioEntity); return portfolioItemEntity;
    }




}
