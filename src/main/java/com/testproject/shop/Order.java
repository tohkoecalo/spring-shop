package com.testproject.shop;

public class Order {
    private String amount;
    private Card card;
    private String orderId;
    private String sessionId;

    public Order(){}

    public Order(String amount, Card card, String orderId, String sessionId){
        this.amount = amount;
        this.card = card;
        this.orderId = orderId;
        this.sessionId = sessionId;
    }

    public String getAmount() {
        return amount;
    }

    public Card getCard() {
        return card;
    }

    public String getOrderId() {
        return orderId;
    }

    public String getSessionId() {
        return sessionId;
    }

    public void setAmount(String amount) {
        this.amount = amount;
    }

    public void setCard(Card card) {
        this.card = card;
    }

    public void setOrderId(String orderId) {
        this.orderId = orderId;
    }

    public void setSessionId(String sessionId) {
        this.sessionId = sessionId;
    }
}
