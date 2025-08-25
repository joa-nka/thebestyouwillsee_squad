package com.neueda.portfolio_managment_app.service;

import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.Map;
import java.math.RoundingMode;

@Service
public class FxService {

    private static final String BASE_CURRENCY = "EUR";
    private static final int INTERNAL_SCALE = 6;
    private static final int DISPLAY_SCALE = 2;

    private static final RoundingMode INTERNAL_ROUNDING = RoundingMode.HALF_EVEN;
    private static final RoundingMode DISPLAY_ROUNDING = RoundingMode.HALF_UP;

    private static final Map<String, BigDecimal> RATES_TO_EUR = Map.of(
            "EUR", BigDecimal.ONE,
            "USD", new BigDecimal("0.93"),
            "PLN", new BigDecimal("0.22"),
            "GBP", new BigDecimal("1.18")
    );

    public BigDecimal convert(BigDecimal amount, String sourceCurrency, String targetCurrency) {
        if (amount == null) {
            return BigDecimal.ZERO.setScale(DISPLAY_SCALE, DISPLAY_ROUNDING);
        }

        String src = normalize(sourceCurrency);
        String tgt = normalize(targetCurrency);

        if (src.equals(tgt)) {
            return amount.setScale(DISPLAY_SCALE, DISPLAY_ROUNDING);
        }

        BigDecimal inPln = amount.multiply(rateToBase(src))
                .setScale(INTERNAL_SCALE, INTERNAL_ROUNDING);

        BigDecimal inTarget = inPln.divide(rateToBase(tgt), INTERNAL_SCALE, INTERNAL_ROUNDING);

        return inTarget.setScale(DISPLAY_SCALE, DISPLAY_ROUNDING);
    }

    private String normalize(String currency) {
        return currency == null ? BASE_CURRENCY : currency.trim().toUpperCase();
    }

    private BigDecimal rateToBase(String currency) {
        return RATES_TO_EUR.getOrDefault(currency, BigDecimal.ONE);
    }

    public BigDecimal convertToBase(BigDecimal amount, String sourceCurrency) {
        return convert(amount, sourceCurrency, BASE_CURRENCY);
    }

}
