package com.eduenroll.beans;

public class AdminAnalyticsBean {
    private String label;
    private int count;
    private double amount;

    public AdminAnalyticsBean() {}

    public String getLabel() {
        return label;
    }

    public void setLabel(String label) {
        this.label = label;
    }

    public int getCount() {
        return count;
    }

    public void setCount(int count) {
        this.count = count;
    }

    public double getAmount() {
        return amount;
    }

    public void setAmount(double amount) {
        this.amount = amount;
    }
}