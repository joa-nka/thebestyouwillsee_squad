package com.neueda.portfolio_managment_app.service;

import com.neueda.portfolio_managment_app.entity.PortfolioEntity;
import com.neueda.portfolio_managment_app.entity.PortfolioItemEntity;
import com.neueda.portfolio_managment_app.repository.PortfolioItemRepository;
import com.neueda.portfolio_managment_app.repository.PortfolioRepository;
import jakarta.persistence.EntityNotFoundException;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class PortfolioItemServiceTest {
    @Mock
    private PortfolioRepository portfolioRepository;

    @Mock
    private PortfolioItemRepository portfolioItemRepository;

    @InjectMocks
    private PortfolioItemService portfolioItemService;

    @Test
    void addItem_savesWithRelation_whenPortfolioExists() {
        PortfolioEntity existingPortfolio = new PortfolioEntity("Tech Growth");
        when(portfolioRepository.findById(1L)).thenReturn(Optional.of(existingPortfolio));
        when(portfolioItemRepository.save(any())).thenAnswer(inv -> inv.getArgument(0));

        PortfolioItemEntity savedItem = portfolioItemService.addItem(1L, "AAPL", 5);

        assertEquals("AAPL", savedItem.getAssetCode());
        assertEquals(5, savedItem.getQuantity());
        assertSame(existingPortfolio, savedItem.getPortfolioEntity());
        verify(portfolioItemRepository).save(savedItem);
    }

    @Test
    void addItem_throwsEntityNotFound_whenPortfolioMissing() {
        when(portfolioRepository.findById(999L)).thenReturn(Optional.empty());

        assertThrows(EntityNotFoundException.class,
                () -> portfolioItemService.addItem(999L, "AAPL", 1));
        verify(portfolioItemRepository, never()).save(any());
    }

    @Test
    void changeQty_updatesQuantityAndSaves() {
        PortfolioItemEntity existingItem = new PortfolioItemEntity("MSFT", 2);
        existingItem.setId(10L);
        when(portfolioItemRepository.findById(10L)).thenReturn(Optional.of(existingItem));
        when(portfolioItemRepository.save(any())).thenAnswer(inv -> inv.getArgument(0));

        PortfolioItemEntity updatedItem = portfolioItemService.changeQty(10L, 7);

        assertEquals(7, updatedItem.getQuantity());
        verify(portfolioItemRepository).save(existingItem);
    }

    @Test
    void findByPortfolio_returnsItemsFromRepository() {
        PortfolioItemEntity appleItem = new PortfolioItemEntity("AAPL", 3);
        PortfolioItemEntity microsoftItem = new PortfolioItemEntity("MSFT", 4);
        when(portfolioItemRepository.findByPortfolioEntityId(1L))
                .thenReturn(List.of(appleItem, microsoftItem));

        List<PortfolioItemEntity> foundItems = portfolioItemService.findByPortfolio(1L);

        assertEquals(2, foundItems.size());
        assertEquals("AAPL", foundItems.get(0).getAssetCode());
        assertEquals("MSFT", foundItems.get(1).getAssetCode());
    }


}
