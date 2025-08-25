package com.neueda.portfolio_managment_app.repository;

import com.neueda.portfolio_managment_app.enumes.Exchange;

import java.math.BigDecimal;

public interface PriceService {
    BigDecimal getCurrentPrice(String symbol, Exchange exchange);
}
