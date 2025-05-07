package com.example.contributorx_android;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.ArrayList;
import java.util.List;

public class APIContributorResponse {
    private boolean isSuccess;
    private String message;
    private Contributor contributor;
    private List<Expectation> expectations = new ArrayList<>();
    private List<Group> groups = new ArrayList<>();
    private List<Grouping> groupings = new ArrayList<>();

    public APIContributorResponse(){
        isSuccess = false;
        this.message = "";
        contributor = null;
    }

    public APIContributorResponse(String message){
        isSuccess = false;
        this.message = message;
        contributor = null;
    }

    @JsonProperty("issuccess")
    public boolean getIsSuccess() {
        return isSuccess;
    }

    public void setIsSuccess(boolean issuccess) {
        this.isSuccess = issuccess;
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

    @JsonProperty("expectations")
    public List<Expectation> getExpectations() {
        return expectations;
    }

    public void setExpectations(List<Expectation> expectations) {
        this.expectations = expectations;
    }

    @JsonProperty("groups")
    public List<Group> getGroups() {
        return groups;
    }

    public void setGroups(List<Group> groups) {
        this.groups = groups;
    }

    @JsonProperty("groupings")
    public List<Grouping> getGroupings() {
        return groupings;
    }

    public void setGroupings(List<Grouping> groupings) {
        this.groupings = groupings;
    }
}
