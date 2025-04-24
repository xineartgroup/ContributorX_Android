package com.example.contributorx_android;

import com.fasterxml.jackson.annotation.JsonProperty;

public class APIContributorResponse {
    private boolean issuccess;
    private String message;
    private Contributor contributor;

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

    @JsonProperty("contributor")
    public Contributor getContributor() {
        return contributor;
    }

    public void setContributor(Contributor contributor) {
        this.contributor = contributor;
    }
}
