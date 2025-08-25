package com.neueda.portfolio_managment_app.service;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;

import java.math.BigDecimal;
import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(MockitoExtension.class)
public class FxServiceTest {

    private final FxService fx = new FxService();
    @Test
    void sameCurrency_noConversion(){
        assertEquals(new BigDecimal("123.46"), fx.convert(new BigDecimal("123.456"), "EUR","EUR"));
    }
    @Test void usd_to_eur_roundsTo2(){
        assertEquals(new BigDecimal("93.00"), fx.convert(new BigDecimal("100"), "USD","EUR"));
    }
    @Test void pln_to_gbp_usesRatesAndRounding(){
        assertEquals(new BigDecimal("18.64"), fx.convert(new BigDecimal("100"), "PLN","GBP"));
    }
}
