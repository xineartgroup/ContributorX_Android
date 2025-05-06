package com.example.contributorx_android;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.ArrayList;
import java.util.List;

public class APIContributorsResponse {
    private boolean isSuccess;
    private String message;
    private List<Contributor> contributors;

    public APIContributorsResponse(){
        this.isSuccess = true;
        this.message = "";
        this.contributors = new ArrayList<>();
    }

    public APIContributorsResponse(String message){
        this.isSuccess = false;
        this.message = message;
        this.contributors = new ArrayList<>();
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

    @JsonProperty("contributors")
    public List<Contributor> getContributors() {
        return contributors;
    }

    public void setContributors(List<Contributor> contributors) {
        this.contributors = contributors;
    }
}
