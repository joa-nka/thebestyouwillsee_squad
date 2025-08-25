package com.neueda.portfolio_managment_app.service;

import com.neueda.portfolio_managment_app.enumes.Exchange;
import com.neueda.portfolio_managment_app.entity.PortfolioItemEntity;
import com.neueda.portfolio_managment_app.repository.PortfolioItemRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.*;
import java.math.BigDecimal;
import java.util.List;
import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.*;

class ReportServiceTest {

    @Mock PortfolioItemRepository repo;
    @Mock FxService fx;
    @Mock PriceServiceCached prices;
    @InjectMocks ReportService report;

    @BeforeEach void init() { MockitoAnnotations.openMocks(this); }

    private PortfolioItemEntity mockItem(String asset, Exchange ex,
                                         BigDecimal buyPrice, int qty) {
        PortfolioItemEntity it = mock(PortfolioItemEntity.class);
        when(it.getAssetCode()).thenReturn(asset);
        when(it.getExchange()).thenReturn(ex);
        when(it.getBuyPrice()).thenReturn(buyPrice);
        when(it.getQuantity()).thenReturn(qty);
        return it;
    }

    @Test
    void holdingsAreSummedPerExchangeInEur() {
        var a = mockItem("PKN", Exchange.WSE, new BigDecimal("10"), 3); // 30 PLN
        var b = mockItem("SAP", Exchange.XETRA, new BigDecimal("20"), 2); // 40 EUR
        when(repo.findByPortfolioEntityId(1L)).thenReturn(List.of(a, b));
        when(fx.convertToBase(new BigDecimal("30"), "PLN")).thenReturn(new BigDecimal("6.60"));
        when(fx.convertToBase(new BigDecimal("40"), "EUR")).thenReturn(new BigDecimal("40.00"));

        var out = report.getPortfolioHoldingsInEur(1L);

        assertThat(out).anySatisfy(row -> {
            if (row.get("exchange").equals("WSE"))
                assertThat(row.get("totalEur")).isEqualTo(new BigDecimal("6.60"));
        });
        assertThat(out).anySatisfy(row -> {
            if (row.get("exchange").equals("XETRA"))
                assertThat(row.get("totalEur")).isEqualTo(new BigDecimal("40.00"));
        });
    }

    @Test
    void purchasesAreListedWithEurValue() {
        var a = mockItem("AAPL", Exchange.NASDAQ, new BigDecimal("100"), 1); // 100 USD
        when(repo.findByPortfolioEntityId(2L)).thenReturn(List.of(a));
        when(fx.convertToBase(new BigDecimal("100"), "USD"))
                .thenReturn(new BigDecimal("93.00"));

        var out = report.getPurchasesInEur(2L);

        assertThat(out).hasSize(1);
        assertThat(out.get(0)).containsEntry("asset", "AAPL")
                .containsEntry("exchange", "NASDAQ")
                .containsEntry("valueEur", new BigDecimal("93.00"));
    }

    @Test
    void currentValueUsesPriceServiceAndFx() {
        var it = mockItem("BP", Exchange.LSE, new BigDecimal("5"), 10);
        when(repo.findByPortfolioEntityId(3L)).thenReturn(List.of(it));
        when(prices.getCurrentPrice("BP", Exchange.LSE)).thenReturn(new BigDecimal("6.50"));
        when(fx.convertToBase(any(BigDecimal.class), eq("GBP")))
                .thenReturn(new BigDecimal("76.70"));

        var out = report.getCurrentValue(3L);

        assertThat(out).singleElement().satisfies(row ->
                assertThat(row.get("currentValueEur")).isEqualTo(new BigDecimal("76.70"))
        );
    }

    @Test
    void profitLossIsCurrentMinusBuyInEur() {
        var it = mockItem("MSFT", Exchange.NASDAQ, new BigDecimal("10"), 5); // buy: 50 USD
        when(repo.findByPortfolioEntityId(4L)).thenReturn(List.of(it));
        when(prices.getCurrentPrice("MSFT", Exchange.NASDAQ)).thenReturn(new BigDecimal("13")); // 65 USD
        when(fx.convertToBase(new BigDecimal("50"), "USD")).thenReturn(new BigDecimal("46.50"));
        when(fx.convertToBase(new BigDecimal("65"), "USD")).thenReturn(new BigDecimal("60.45"));

        var out = report.getProfitLoss(4L);

        assertThat(out).singleElement().satisfies(row -> {
            assertThat(row.get("buyValueEur")).isEqualTo(new BigDecimal("46.50"));
            assertThat(row.get("currentValueEur")).isEqualTo(new BigDecimal("60.45"));
            assertThat(row.get("profitLossEur")).isEqualTo(new BigDecimal("13.95"));
        });
    }
}
