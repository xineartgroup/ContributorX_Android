package com.example.contributorx_android;

import java.util.Calendar;

public class Group {

    private int id;
    private String name;
    private String description;
    private int communityId;
    private Calendar dateCreated = Calendar.getInstance();

    public Group() {

    }

    public Group(String name, String description, int communityId) {
        this.name = name;
        this.description = description;
        this.communityId = communityId;
    }

    public Group(String name) {
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

    public String getDescription() { return description; }

    public void setDescription(String description) { this.description = description; }

    public int getCommunityId() { return communityId; }

    public void setCommunityId(int communityId) { this.communityId = communityId; }

    public Calendar getDateCreated() {
        return dateCreated;
    }

    public void setDateCreated(Calendar dateCreated) {
        this.dateCreated = dateCreated;
    }
}
