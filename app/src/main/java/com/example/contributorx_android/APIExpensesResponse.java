package com.example.contributorx_android;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.ArrayList;
import java.util.List;

public class APIExpensesResponse {
    private boolean isSuccess;
    private String message;
    private List<Expense> expenses;

    public APIExpensesResponse(){
        this.isSuccess = true;
        this.message = "";
        this.expenses = new ArrayList<>();
    }

    public APIExpensesResponse(String message){
        this.isSuccess = false;
        this.message = message;
        this.expenses = new ArrayList<>();
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

    @JsonProperty("expenses")
    public List<Expense> getExpenses() {
        return expenses;
    }

    public void setExpenses(List<Expense> expenses) {
        this.expenses = expenses;
    }
}
