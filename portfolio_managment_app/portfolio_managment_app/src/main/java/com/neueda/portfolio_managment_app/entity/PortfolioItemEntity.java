package com.neueda.portfolio_managment_app.entity;

import jakarta.persistence.*;

@Entity
public class PortfolioItemEntity {

    @Id
    @GeneratedValue
    private Long id;

    private String assetCode;
    private int quantity;


    public PortfolioItemEntity(Long id, String ticker, int quantity) {
        this.id = id;
        this.assetCode = ticker;
        this.quantity = quantity;
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
    @ManyToOne
    @JoinColumn(name = "portfolioItem_id")
    private PortfolioEntity portfolioEntity;

}
