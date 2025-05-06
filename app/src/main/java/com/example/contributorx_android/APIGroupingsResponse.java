package com.example.contributorx_android;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.ArrayList;
import java.util.List;

public class APIGroupingsResponse {
    private boolean isSuccess;
    private String message;
    private List<Grouping> groupings;

    public APIGroupingsResponse(){
        this.isSuccess = true;
        this.message = "";
        this.groupings = new ArrayList<>();
    }

    public APIGroupingsResponse(String message){
        this.isSuccess = false;
        this.message = message;
        this.groupings = new ArrayList<>();
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

    @JsonProperty("groupings")
    public List<Grouping> getGroupings() {
        return groupings;
    }

    public void setGroupings(List<Grouping> groupings) {
        this.groupings = groupings;
    }
}
