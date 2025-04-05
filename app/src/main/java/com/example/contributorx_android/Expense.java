package com.example.contributorx_android;

import java.util.Calendar;

public class Expense {

    private int id;
    private String name;
    private String description;
    private Calendar dateCreated = Calendar.getInstance();
    private float amountPaid = 0.00f;
    private int communityId;
    private String paymentReceipt = "";

    public Expense() {

    }

    public Expense(String name, String description, int communityId) {
        this.name = name;
        this.description = description;
        this.communityId = communityId;
    }

    public Expense(String name) {
        this.name = name;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Calendar getDateCreated() {
        return dateCreated;
    }

    public void setDateCreated(Calendar dateCreated) {
        this.dateCreated = dateCreated;
    }

    public float getAmountPaid() {
        return amountPaid;
    }

    public void setAmountPaid(float amountPaid) {
        this.amountPaid = amountPaid;
    }

    public int getCommunityId() {
        return communityId;
    }

    public void setCommunityId(int communityId) {
        this.communityId = communityId;
    }

    public String getPaymentReceipt() { return paymentReceipt; }

    public void setPaymentReceipt(String paymentReceipt) { this.paymentReceipt = paymentReceipt; }
}
