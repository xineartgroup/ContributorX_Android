package com.example.contributorx_android;

import com.fasterxml.jackson.annotation.JsonProperty;

public class APICommunityResponse {
    private boolean isSuccess;
    private String message;
    private Community community;

    public APICommunityResponse(){

    }

    public APICommunityResponse(String message){
        isSuccess = false;
        this.message = message;
        community = null;
    }

    @JsonProperty("issuccess")
    public boolean getSuccess() {
        return isSuccess;
    }

    public void setSuccess(boolean success) {
        this.isSuccess = success;
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
