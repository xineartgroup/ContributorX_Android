package com.example.contributorx_android;

import com.fasterxml.jackson.annotation.JsonProperty;

public class APIExpenseResponse {
    private boolean isSuccess;
    private String message;
    private Expense expense;

    public APIExpenseResponse(){
        this.isSuccess = true;
        this.message = "";
        this.expense = null;
    }

    public APIExpenseResponse(String message){
        this.isSuccess = false;
        this.message = message;
        this.expense = null;
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

    @JsonProperty("expense")
    public Expense getExpense() {
        return expense;
    }

    public void setExpense(Expense expense) {
        this.expense = expense;
    }
}
