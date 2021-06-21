package com.testproject.shop;

public class Order {
    private String amount;
    private Card card;

    public Order(){}

    public Order(String amount, Card card){
        this.amount = amount;
        this.card = card;
    }

    public String getAmount() {
        return amount;
    }

    public Card getCard() {
        return card;
    }

    public void setAmount(String amount) {
        this.amount = amount;
    }

    public void setCard(Card card) {
        this.card = card;
    }
}
