package com.neueda.portfolio_managment_app.service;

import java.math.BigDecimal;
import java.util.Map;
import java.math.RoundingMode;

public class FxService {

    private static final String BASE_CURRENCY = "PLN";
    private static final int INTERNAL_SCALE = 6;
    private static final int DISPLAY_SCALE = 2;

    private static final RoundingMode INTERNAL_ROUNDING = RoundingMode.HALF_EVEN;
    private static final RoundingMode DISPLAY_ROUNDING = RoundingMode.HALF_UP;

    private static final Map<String, BigDecimal> RATES_TO_PLN = Map.of(
            "PLN", BigDecimal.ONE,
            "USD", new BigDecimal("4.20"),
            "EUR", new BigDecimal("4.50"),
            "GBP", new BigDecimal("5.30")
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

        BigDecimal inPln = amount.multiply(rateToPln(src))
                .setScale(INTERNAL_SCALE, INTERNAL_ROUNDING);

        BigDecimal inTarget = inPln.divide(rateToPln(tgt), INTERNAL_SCALE, INTERNAL_ROUNDING);

        return inTarget.setScale(DISPLAY_SCALE, DISPLAY_ROUNDING);
    }

    private String normalize(String currency) {
        return currency == null ? BASE_CURRENCY : currency.trim().toUpperCase();
    }

    private BigDecimal rateToPln(String currency) {
        return RATES_TO_PLN.getOrDefault(currency, BigDecimal.ONE);
    }

}
