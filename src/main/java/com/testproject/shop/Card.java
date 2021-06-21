package com.testproject.shop;

public class Card {
    private String number;
    private String expMonth;
    private String expYear;
    private String cvv;

    public Card(){}

    public Card(String number, String expMonth, String expYear, String cvv){
        this.number = number;
        this.expMonth = expMonth;
        this.expYear = expYear;
        this.cvv = cvv;
    }

    public String getNumber() {
        return number;
    }

    public String getExpMonth() {
        return expMonth;
    }

    public String getExpYear() {
        return expYear;
    }

    public String getCvv() {
        return this.cvv;
    }

    public void setNumber(String number) {
        this.number = number;
    }

    public void setExpMonth(String expMonth) {
        this.expMonth = expMonth;
    }

    public void setExpYear(String expYear) {
        this.expYear = expYear;
    }

    public void setCvv(String cvv){
        this.cvv = cvv;
    }
}
