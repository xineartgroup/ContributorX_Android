package com.example.contributorx_android;

import java.util.Calendar;

public class Contribution {
    private int id = 0;
    private String name = "";
    private float amount = 0.00f;
    private int groupId = 0;
    private Calendar dateCreated = Calendar.getInstance();
    private Calendar dueDate = Calendar.getInstance();

    public Contribution() {

    }

    public Contribution(String name, float amount, int groupId, Calendar dateCreated, Calendar dueDate) {
        this.name = name;
        this.amount = amount;
        this.groupId = groupId;
        this.dateCreated = dateCreated;
        this.dueDate = dueDate;
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

    public float getAmount() {
        return amount;
    }

    public void setAmount(float amount) {
        this.amount = amount;
    }

    public int getGroupId() {
        return groupId;
    }

    public void setGroupId(int groupId) {
        this.groupId = groupId;
    }

    public Calendar getDateCreated() {
        return dateCreated;
    }

    public void setDateCreated(Calendar dateCreated) {
        this.dateCreated = dateCreated;
    }

    public Calendar getDueDate() {
        return dueDate;
    }

    public void setDueDate(Calendar dueDate) {
        this.dueDate = dueDate;
    }
}
