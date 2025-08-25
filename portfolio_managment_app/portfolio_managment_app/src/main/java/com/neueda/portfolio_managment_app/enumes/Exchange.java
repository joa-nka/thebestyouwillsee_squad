package com.neueda.portfolio_managment_app.enumes;

public enum Exchange {

    WSE  ("Warsaw Stock Exchange","PL","PLN"),
    XETRA("Deutsche Börse Xetra","DE","EUR"),
    NASDAQ("NASDAQ","US","USD"),
    NYSE ("New York Stock Exchange","US","USD"),
    LSE  ("London Stock Exchange","GB","GBP");

    private final String displayName;
    private  final String country;
    private final String currency;

    Exchange(String displayName, String country, String currency) {
        this.displayName = displayName;
        this.country = country;
        this.currency = currency;
    }
    public String getDisplayName() {
        return displayName;
    }

    public String getCountry() {
        return country;
    }

    public String getCurrency() {
        return currency;
    }


}
