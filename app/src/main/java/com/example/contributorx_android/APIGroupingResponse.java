package com.example.contributorx_android;

import com.fasterxml.jackson.annotation.JsonProperty;

public class APIGroupingResponse {
    private boolean isSuccess;
    private String message;
    private Grouping grouping;

    public APIGroupingResponse(){
        this.isSuccess = true;
        this.message = "";
        this.grouping = null;
    }

    public APIGroupingResponse(String message){
        this.isSuccess = false;
        this.message = message;
        this.grouping = null;
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

    @JsonProperty("grouping")
    public Grouping getGrouping() {
        return grouping;
    }

    public void setGrouping(Grouping grouping) {
        this.grouping = grouping;
    }
}
