package com.example.contributorx_android;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.ArrayList;
import java.util.List;

public class APIResponse {
    private boolean isSuccess;
    private String message;
    private Community community;
    private Contribution contribution;
    private Contributor contributor;
    private Expectation expectation;
    private Expense expense;
    private Group group;
    private Grouping grouping;
    private List<Community> communities = new ArrayList<>();
    private List<Contribution> contributions = new ArrayList<>();
    private List<Contributor> contributors = new ArrayList<>();
    private List<Expectation> expectations = new ArrayList<>();
    private List<Expense> expenses = new ArrayList<>();
    private List<Grouping> groupings = new ArrayList<>();
    private List<Group> groups = new ArrayList<>();

    public APIResponse(){
        isSuccess = false;
        this.message = "";
        contributor = null;
    }

    public APIResponse(String message){
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

    @JsonProperty("community")
    public Community getCommunity() {
        return community;
    }

    public void setCommunity(Community community) {
        this.community = community;
    }

    @JsonProperty("contribution")
    public Contribution getContribution() {
        return contribution;
    }

    public void setContribution(Contribution contribution) {
        this.contribution = contribution;
    }

    @JsonProperty("contributor")
    public Contributor getContributor() {
        return contributor;
    }

    public void setContributor(Contributor contributor) {
        this.contributor = contributor;
    }

    @JsonProperty("expectation")
    public Expectation getExpectation() {
        return expectation;
    }

    public void setExpectation(Expectation expectation) {
        this.expectation = expectation;
    }

    @JsonProperty("expense")
    public Expense getExpense() {
        return expense;
    }

    public void setExpense(Expense expense) {
        this.expense = expense;
    }

    @JsonProperty("grouping")
    public Grouping getGrouping() {
        return grouping;
    }

    public void setGrouping(Grouping grouping) {
        this.grouping = grouping;
    }

    @JsonProperty("group")
    public Group getGroup() {
        return group;
    }

    public void setGroup(Group group) {
        this.group = group;
    }

    @JsonProperty("communities")
    public List<Community> getCommunities() {
        return communities;
    }

    public void setCommunities(List<Community> communities) {
        this.communities = communities;
    }

    @JsonProperty("contributions")
    public List<Contribution> getContributions() {
        return contributions;
    }

    public void setContributions(List<Contribution> contributions) {
        this.contributions = contributions;
    }

    @JsonProperty("contributors")
    public List<Contributor> getContributors() {
        return contributors;
    }

    public void setContributors(List<Contributor> contributors) {
        this.contributors = contributors;
    }

    @JsonProperty("expectations")
    public List<Expectation> getExpectations() {
        return expectations;
    }

    public void setExpectations(List<Expectation> expectations) {
        this.expectations = expectations;
    }

    @JsonProperty("expenses")
    public List<Expense> getExpenses() {
        return expenses;
    }

    public void setExpenses(List<Expense> expenses) {
        this.expenses = expenses;
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
