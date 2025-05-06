package com.example.contributorx_android;

import com.fasterxml.jackson.annotation.JsonProperty;

public class APICommunityResponse {
    private boolean issuccess;
    private String message;
    private Community community;

    public APICommunityResponse(){

    }

    public APICommunityResponse(String message){
        issuccess = false;
        this.message = message;
        community = null;
    }

    @JsonProperty("issuccess")
    public boolean getIssuccess() {
        return issuccess;
    }

    public void setIssuccess(boolean issuccess) {
        this.issuccess = issuccess;
    }

    @JsonProperty("message")
    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    @JsonProperty("community")
    public Community getCommunity() {
        return community;
    }

    public void setCommunity(Community community) {
        this.community = community;
    }
}
