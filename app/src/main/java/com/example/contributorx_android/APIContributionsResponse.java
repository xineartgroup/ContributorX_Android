package com.example.contributorx_android;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.ArrayList;
import java.util.List;

public class APIContributionsResponse {
    private boolean isSuccess;
    private String message;
    private List<Contribution> contributions;

    public APIContributionsResponse(){
        this.isSuccess = true;
        this.message = "";
        this.contributions = new ArrayList<>();
    }

    public APIContributionsResponse(String message){
        this.isSuccess = false;
        this.message = message;
        this.contributions = new ArrayList<>();
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

    @JsonProperty("contributions")
    public List<Contribution> getContributions() {
        return contributions;
    }

    public void setContributions(List<Contribution> contributions) {
        this.contributions = contributions;
    }
}
