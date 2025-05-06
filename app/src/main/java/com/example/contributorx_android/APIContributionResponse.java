package com.example.contributorx_android;

import com.fasterxml.jackson.annotation.JsonProperty;

public class APIContributionResponse {
    private boolean isSuccess;
    private String message;
    private Contribution contribution;

    public APIContributionResponse(){
        this.isSuccess = true;
        this.message = "";
        this.contribution = null;
    }

    public APIContributionResponse(String message){
        this.isSuccess = false;
        this.message = message;
        this.contribution = null;
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

    @JsonProperty("contribution")
    public Contribution getContribution() {
        return contribution;
    }

    public void setContribution(Contribution contribution) {
        this.contribution = contribution;
    }
}
