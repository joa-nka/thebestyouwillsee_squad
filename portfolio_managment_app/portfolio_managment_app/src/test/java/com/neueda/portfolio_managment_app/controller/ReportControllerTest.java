package com.neueda.portfolio_managment_app.controller;

import com.neueda.portfolio_managment_app.entity.PortfolioItemEntity;
import com.neueda.portfolio_managment_app.enumes.Exchange;
import com.neueda.portfolio_managment_app.repository.PortfolioItemRepository;
import com.neueda.portfolio_managment_app.service.FxService;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.web.servlet.MockMvc;
import java.math.BigDecimal;
import java.util.List;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;
@WebMvcTest(ReportController.class)
class ReportControllerTest {
    @Autowired MockMvc mvc;
    @MockBean PortfolioItemRepository repo;
    @MockBean FxService fx;

    @Test void returnsTotalsPerExchange() throws Exception {
        var item = Mockito.mock(PortfolioItemEntity.class);
        var ex = Exchange.values()[0];
        when(item.getBuyPrice()).thenReturn(new BigDecimal("10"));
        when(item.getQuantity()).thenReturn(3);
        when(item.getExchange()).thenReturn(ex);
        when(repo.findByPortfolioEntityId(1L)).thenReturn(List.of(item));
        when(fx.convertToBase(new BigDecimal("30"), ex.getCurrency()))
                .thenReturn(new BigDecimal("30.00"));
        mvc.perform(get("/reports/portfolio/1/holdings-eur"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].exchange").value(ex.name()))
                .andExpect(jsonPath("$[0].totalEur").value(30.00));
    }

    @Test void emptyPortfolio_returnsEmptyArray() throws Exception {
        when(repo.findByPortfolioEntityId(999L)).thenReturn(List.of());
        mvc.perform(get("/reports/portfolio/999/holdings-eur"))
                .andExpect(status().isOk())
                .andExpect(content().json("[]"));
    }

    @Test
    void purchasesInEur_returnsListWithOneItem() throws Exception {
        var item = Mockito.mock(PortfolioItemEntity.class);
        var ex = Exchange.values()[0];

        when(item.getBuyPrice()).thenReturn(new BigDecimal("50"));
        when(item.getQuantity()).thenReturn(2);
        when(item.getExchange()).thenReturn(ex);
        when(item.getAssetCode()).thenReturn("AAPL");

        when(repo.findByPortfolioEntityId(1L)).thenReturn(List.of(item));
        when(fx.convertToBase(new BigDecimal("100"), ex.getCurrency()))
                .thenReturn(new BigDecimal("100.00"));

        mvc.perform(get("/reports/portfolio/1/purchases-eur"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].asset").value("AAPL"))
                .andExpect(jsonPath("$[0].exchange").value(ex.name()))
                .andExpect(jsonPath("$[0].valueEur").value(100.00));
    }

    @Test
    void purchasesInEur_emptyPortfolio_returnsEmptyArray() throws Exception {
        when(repo.findByPortfolioEntityId(99L)).thenReturn(List.of());

        mvc.perform(get("/reports/portfolio/99/purchases-eur"))
                .andExpect(status().isOk())
                .andExpect(content().json("[]"));
    }
}
