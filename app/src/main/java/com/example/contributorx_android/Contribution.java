package com.example.contributorx_android;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.Calendar;

public class Contribution {
    private int id = 0;
    private String name = "";
    private float amount = 0.00f;
    private int groupId = 0;
    private String dateCreated = "";
    private String dueDate = "";

    public Contribution() {

    }

    public Contribution(String name, float amount, int groupId, String dateCreated, String dueDate) {
        this.name = name;
        this.amount = amount;
        this.groupId = groupId;
        this.dateCreated = dateCreated;
        this.dueDate = dueDate;
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

    @JsonProperty("Amount")
    public float getAmount() {
        return amount;
    }

    public void setAmount(float amount) {
        this.amount = amount;
    }

    @JsonProperty("GroupId")
    public int getGroupId() {
        return groupId;
    }

    public void setGroupId(int groupId) {
        this.groupId = groupId;
    }

    @JsonProperty("DateCreated")
    public String getDateCreated() {
        return dateCreated;
    }

    public void setDateCreated(String dateCreated) {
        this.dateCreated = dateCreated;
    }

    @JsonProperty("DueDate")
    public String getDueDate() {
        return dueDate;
    }

    public void setDueDate(String dueDate) {
        this.dueDate = dueDate;
    }
}
