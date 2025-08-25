package com.neueda.portfolio_managment_app.entity;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.neueda.portfolio_managment_app.enumes.Exchange;
import jakarta.persistence.*;

import java.math.BigDecimal;
import java.time.LocalDate;

@Entity
public class PortfolioItemEntity {

    @Id
    @GeneratedValue
    private Long id;

   @Column(nullable = false)
    private String assetCode;

    @Column(nullable = false)
    private int quantity;

    @Column(nullable = false, precision = 19, scale = 4)
    private BigDecimal buyPrice = BigDecimal.ZERO;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private Exchange exchange;

    @Column(nullable = true)
    private LocalDate tradeDate;

    @ManyToOne(optional = false, fetch = FetchType.LAZY)
    @JoinColumn(name = "portfolio_id", nullable = false)
    @JsonBackReference
    private PortfolioEntity portfolioEntity;


    public PortfolioItemEntity (String assetCode, int quantity, BigDecimal buyPrice, Exchange exchange, PortfolioEntity portfolio, LocalDate tradeDate) {
        this.assetCode = assetCode;
        this.quantity = quantity;
        this.buyPrice = buyPrice != null ? buyPrice : BigDecimal.ZERO;
        this.portfolioEntity = portfolio;
        this.exchange = exchange;
        this.tradeDate = tradeDate;
    }


    public PortfolioItemEntity() {

    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getAssetCode() {
        return assetCode;
    }

    public void setAssetCode(String assetCode) {
        this.assetCode = assetCode;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    public Exchange getExchange() {
        return exchange;
    }

    public void setExchange(Exchange exchange) {
        this.exchange = exchange;
    }

     public  BigDecimal getBuyPrice() {
        return buyPrice;
     }

     public void setBuyPrice(BigDecimal buyPrice){
        this.buyPrice = buyPrice;

     }

    public LocalDate getBuyDate() {
        return tradeDate;
    }

    public PortfolioEntity getPortfolioEntity() { return portfolioEntity; }
    public void setPortfolioEntity(PortfolioEntity portfolioEntity) {
        this.portfolioEntity = portfolioEntity; }

    public LocalDate getTradeDate() {
        return tradeDate;
    }
    public void setTradeDate(LocalDate tradeDate) {
        this.tradeDate = tradeDate;
    }

    @PrePersist
    void prePersist() {
        if (tradeDate == null) tradeDate = LocalDate.now();
    }


    public Exchange getExchange() {
        return exchange;
    }
}
