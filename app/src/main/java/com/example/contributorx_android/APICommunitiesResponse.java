package com.example.contributorx_android;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.ArrayList;
import java.util.List;

public class APICommunitiesResponse {
    private boolean isSuccess;
    private String message;
    private List<Community> communities;

    public APICommunitiesResponse(){
        this.isSuccess = true;
        this.message = "";
        this.communities = new ArrayList<>();
    }

    public APICommunitiesResponse(String message){
        this.isSuccess = false;
        this.message = message;
        this.communities = new ArrayList<>();
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

    @JsonProperty("communities")
    public List<Community> getCommunities() {
        return communities;
    }

    public void setCommunities(List<Community> communities) {
        this.communities = communities;
    }
}
