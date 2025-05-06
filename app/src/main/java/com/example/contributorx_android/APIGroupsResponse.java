package com.example.contributorx_android;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.ArrayList;
import java.util.List;

public class APIGroupsResponse {
    private boolean isSuccess;
    private String message;
    private List<Group> groups;

    public APIGroupsResponse(){
        this.isSuccess = true;
        this.message = "";
        this.groups = new ArrayList<>();
    }

    public APIGroupsResponse(String message){
        this.isSuccess = false;
        this.message = message;
        this.groups = new ArrayList<>();
    }

    @JsonProperty("issuccess")
    public boolean getIsSuccess() {
        return isSuccess;
    }

    public void setIsSuccess(boolean isSuccess) {
        this.isSuccess = isSuccess;
    }

    @JsonProperty("message")
    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    @JsonProperty("groups")
    public List<Group> getGroups() {
        return groups;
    }

    public void setGroups(List<Group> groups) {
        this.groups = groups;
    }
}
