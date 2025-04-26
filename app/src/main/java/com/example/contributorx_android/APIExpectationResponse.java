package com.example.contributorx_android;

import com.fasterxml.jackson.annotation.JsonProperty;

public class APIExpectationResponse {
    private boolean issuccess;
    private String message;
    private Expectation expectation;

    public APIExpectationResponse(){

    }

    public APIExpectationResponse(String message){
        issuccess = false;
        this.message = message;
        expectation = null;
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

    @JsonProperty("expectation")
    public Expectation getExpectation() {
        return expectation;
    }

    public void setExpectation(Expectation expectation) {
        this.expectation = expectation;
    }
}
