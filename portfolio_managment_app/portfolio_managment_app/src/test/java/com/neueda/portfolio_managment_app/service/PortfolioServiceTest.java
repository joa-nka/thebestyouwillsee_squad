package com.neueda.portfolio_managment_app.service;

import com.neueda.portfolio_managment_app.entity.PortfolioEntity;
import com.neueda.portfolio_managment_app.repository.PortfolioRepository;
import jakarta.persistence.EntityNotFoundException;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class PortfolioServiceTest {
    @Mock
    private PortfolioRepository portfolioRepository;

    @InjectMocks
    private PortfolioService portfolioService;

    @Test
    void create_savesAndReturnsEntity() {
        PortfolioEntity savedPortfolio = new PortfolioEntity("Main");
        when(portfolioRepository.save(any())).thenReturn(savedPortfolio);

        PortfolioEntity result = portfolioService.create("Main");

        assertEquals("Main", result.getName());
        verify(portfolioRepository).save(any());
    }

    @Test
    void findById_returnsEntity_whenExists() {
        PortfolioEntity portfolio = new PortfolioEntity("Growth");
        portfolio.setId(1L);
        when(portfolioRepository.findById(1L)).thenReturn(Optional.of(portfolio));

        PortfolioEntity result = portfolioService.findById(1L);

        assertEquals("Growth", result.getName());
    }

    @Test
    void findById_throwsException_whenNotFound() {
        when(portfolioRepository.findById(99L)).thenReturn(Optional.empty());

        assertThrows(EntityNotFoundException.class, () -> portfolioService.findById(99L));
    }

    @Test
    void findAll_returnsAllPortfolios() {
        PortfolioEntity portfolio1 = new PortfolioEntity("Growth");
        PortfolioEntity portfolio2 = new PortfolioEntity("Income");
        when(portfolioRepository.findAll()).thenReturn(Arrays.asList(portfolio1, portfolio2));

        List<PortfolioEntity> portfolios = portfolioService.findAll();

        assertEquals(2, portfolios.size());
        assertEquals("Growth", portfolios.get(0).getName());
        assertEquals("Income", portfolios.get(1).getName());
    }

    @Test
    void rename_updatesName() {
        PortfolioEntity existingPortfolio = new PortfolioEntity("Old");
        existingPortfolio.setId(1L);
        when(portfolioRepository.findById(1L)).thenReturn(Optional.of(existingPortfolio));

        PortfolioEntity updatedPortfolio = portfolioService.rename(1L, "New");

        assertEquals("New", updatedPortfolio.getName());
    }

    @Test
    void delete_removesPortfolio() {
        doNothing().when(portfolioRepository).deleteById(1L);

        portfolioService.delete(1L);

        verify(portfolioRepository).deleteById(1L);
    }
}
