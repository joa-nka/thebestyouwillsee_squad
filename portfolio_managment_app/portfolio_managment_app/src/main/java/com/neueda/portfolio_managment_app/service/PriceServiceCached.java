package com.neueda.portfolio_managment_app.service;
import com.neueda.portfolio_managment_app.enumes.Exchange;
import com.neueda.portfolio_managment_app.service.PriceService;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.concurrent.ConcurrentHashMap;
import java.util.Map;


@Service
public class PriceServiceCached  {
    private final Map<String, BigDecimal> cache = new ConcurrentHashMap<>();

    private BigDecimal compute(String symbol, Exchange ex){
        int baseValue = Math.abs(symbol.toUpperCase().hashCode() % 300) + 50;
        BigDecimal exchangeFactor = BigDecimal.valueOf(1.00 + (ex.ordinal() % 5) * 0.01);
        return BigDecimal.valueOf(baseValue).multiply(exchangeFactor).setScale(2, RoundingMode.HALF_UP);
    }

    @Override public BigDecimal getCurrentPrice(String symbol, Exchange ex){
        String key = symbol.toUpperCase() + "@" + ex.name();
        return cache.computeIfAbsent(key, k -> compute(symbol, ex));
    }
}
