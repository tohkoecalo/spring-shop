package com.testproject.shop;

//@Component
public class Card {
    private String number;
    private String expiry;
    private String cvv;

    public Card(){}

    public Card(String number, String expiry, String cvv){
        this.number = number;
        this.expiry = expiry;
        this.cvv = cvv;
    }

    public String getNumber() {
        return number;
    }

    public String getExpiry() {
        return expiry;
    }

    public String getCvv() {
        return this.cvv;
    }

    public void setNumber(String number) {
        this.number = number;
    }

    public void setExpiry(String expiry) {
        this.expiry = expiry;
    }


    public void setCvv(String cvv){
        this.cvv = cvv;
    }
}
