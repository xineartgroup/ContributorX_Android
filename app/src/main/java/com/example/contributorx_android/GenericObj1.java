package com.example.contributorx_android;

import com.fasterxml.jackson.annotation.JsonProperty;

public class GenericObj1 {
    private int id = 0;
    private boolean active = true;
    private String groups = "";

    @JsonProperty("id")
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    @JsonProperty("IsActive")
    public boolean isActive() {
        return active;
    }

    public void setActive(boolean active) {
        this.active = active;
    }

    @JsonProperty("groups")
    public String getGroups() {
        return groups;
    }

    public void setGroups(String groups) {
        this.groups = groups;
    }
}
