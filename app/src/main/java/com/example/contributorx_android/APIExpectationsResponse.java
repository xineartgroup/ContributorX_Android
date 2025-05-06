package com.example.contributorx_android;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.ArrayList;
import java.util.List;

public class APIExpectationsResponse {
    private boolean isSuccess;
    private String message;
    private List<Expectation> expectations;

    public APIExpectationsResponse(){
        this.isSuccess = true;
        this.message = "";
        this.expectations = new ArrayList<>();
    }

    public APIExpectationsResponse(String message){
        this.isSuccess = false;
        this.message = message;
        this.expectations = new ArrayList<>();
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

    @JsonProperty("expectations")
    public List<Expectation> getExpectations() {
        return expectations;
    }

    public void setExpectations(List<Expectation> expectations) {
        this.expectations = expectations;
    }
}
