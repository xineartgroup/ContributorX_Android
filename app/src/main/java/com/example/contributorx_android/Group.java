package com.example.contributorx_android;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.Calendar;

public class Group {

    private int id = 0;
    private String name = "";
    private String description = "";
    private int communityId = 0;
    private String dateCreated = Calendar.getInstance().getTime().toString();

    public Group() {

    }

    public Group(String name, String description, int communityId) {
        this.name = name;
        this.description = description;
        this.communityId = communityId;
        this.dateCreated = Calendar.getInstance().getTime().toString();
    }

    public Group(String name) {
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
    public String getDescription() { return description; }

    public void setDescription(String description) { this.description = description; }

    @JsonProperty("CommunityId")
    public int getCommunityId() { return communityId; }

    public void setCommunityId(int communityId) { this.communityId = communityId; }

    @JsonProperty("DateCreated")
    public String getDateCreated() {
        return dateCreated;
    }

    public void setDateCreated(String dateCreated) {
        this.dateCreated = dateCreated;
    }
}
