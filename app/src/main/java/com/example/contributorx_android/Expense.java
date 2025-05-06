package com.example.contributorx_android;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.Calendar;

public class Expense {

    private int id = 0;
    private String name = "";
    private String description = "";
    private String dateCreated = Calendar.getInstance().getTime().toString();
    private float amountPaid = 0.00f;
    private int communityId = 0;
    private String paymentReceipt = "";

    public Expense() {

    }

    public Expense(String name, String description, float amountPaid, int communityId, String paymentReceipt) {
        this.name = name;
        this.description = description;
        this.dateCreated = Calendar.getInstance().getTime().toString();
        this.amountPaid = amountPaid;
        this.communityId = communityId;
        this.paymentReceipt = paymentReceipt;
    }

    public Expense(String name) {
        this.name = name;
    }

    @JsonProperty("Id")
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    @JsonProperty("Name")
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @JsonProperty("Description")
    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    @JsonProperty("DateCreated")
    public String getDateCreated() {
        return dateCreated;
    }

    public void setDateCreated(String dateCreated) {
        this.dateCreated = dateCreated;
    }

    @JsonProperty("AmountPaid")
    public float getAmountPaid() {
        return amountPaid;
    }

    public void setAmountPaid(float amountPaid) {
        this.amountPaid = amountPaid;
    }

    @JsonProperty("CommunityId")
    public int getCommunityId() {
        return communityId;
    }

    public void setCommunityId(int communityId) {
        this.communityId = communityId;
    }

    @JsonProperty("PaymentReceipt")
    public String getPaymentReceipt() { return paymentReceipt; }

    public void setPaymentReceipt(String paymentReceipt) { this.paymentReceipt = paymentReceipt; }
}
