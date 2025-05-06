package com.example.contributorx_android;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.Calendar;

public class Community {

    private int id;
    private String name;
    private String description;
    private Calendar dateCreated = Calendar.getInstance();

    public Community() {

    }

    public Community(String name, String description) {
        this.name = name;
        this.description = description;
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
    public Calendar getDateCreated() {
        return dateCreated;
    }

    public void setDateCreated(Calendar dateCreated) {
        this.dateCreated = dateCreated;
    }
}
