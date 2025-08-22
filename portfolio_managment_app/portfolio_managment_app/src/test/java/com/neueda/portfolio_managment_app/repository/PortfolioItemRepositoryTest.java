package com.neueda.portfolio_managment_app.repository;

import com.neueda.portfolio_managment_app.entity.PortfolioEntity;
import com.neueda.portfolio_managment_app.entity.PortfolioItemEntity;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import static org.junit.jupiter.api.Assertions.assertEquals;

@DataJpaTest
public class PortfolioItemRepositoryTest {

    @Autowired
    PortfolioRepository portfolioRepository;

    @Autowired
    PortfolioItemRepository portfolioItemRepository;

    @BeforeEach
    void clearDataBase(){
        portfolioRepository.deleteAll();
        portfolioItemRepository.deleteAll();
    }

    @Test
    void findByPortfolioEntityId_returnsItems() {
        var testPortfolio1 = portfolioRepository.save(new PortfolioEntity("Main"));
        var testPorfolio2 = portfolioRepository.save(new PortfolioEntity("Other"));
        var testItem1 = new PortfolioItemEntity("AAPL",5); testItem1.setPortfolioEntity(testPortfolio1); portfolioItemRepository.save(testItem1);
        var testItem2 = new PortfolioItemEntity("GOOG",2); testItem2.setPortfolioEntity(testPortfolio1); portfolioItemRepository.save(testItem2);
        var testItem3 = new PortfolioItemEntity("TSLA",1); testItem3.setPortfolioEntity(testPorfolio2); portfolioItemRepository.save(testItem3);

        var found = portfolioItemRepository.findByPortfolioEntityId(testPortfolio1.getId());
        assertEquals(2, found.size());
    }
}
