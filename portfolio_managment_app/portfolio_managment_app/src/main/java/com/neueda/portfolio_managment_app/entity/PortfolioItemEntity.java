package com.neueda.portfolio_managment_app.entity;

import jakarta.persistence.*;

@Entity
public class PortfolioItemEntity {

    @Id
    @GeneratedValue
    private Long id;

   @Column(nullable = false)
    private String assetCode;

    private int quantity;


    public PortfolioItemEntity(Long id, String ticker, int quantity) {
        this.id = id;
        this.assetCode = ticker;
        this.quantity = quantity;
    }

    public PortfolioItemEntity(String ticker, int quantity) {
        this.assetCode = ticker;
        this.quantity = quantity;
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

    public PortfolioEntity getPortfolioEntity() {
        return portfolioEntity;
    }

    public void setPortfolioEntity(PortfolioEntity portfolioEntity) {
        this.portfolioEntity = portfolioEntity;
    }

    @ManyToOne
    @JoinColumn(name = "portfolioItem_id")
    private PortfolioEntity portfolioEntity;

}
