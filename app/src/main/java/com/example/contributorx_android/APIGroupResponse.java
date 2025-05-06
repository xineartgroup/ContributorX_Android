package com.example.contributorx_android;

import com.fasterxml.jackson.annotation.JsonProperty;

public class APIGroupResponse {
    private boolean isSuccess;
    private String message;
    private Group group;

    public APIGroupResponse(){
        this.isSuccess = true;
        this.message = "";
        this.group = null;
    }

    public APIGroupResponse(String message){
        this.isSuccess = false;
        this.message = message;
        this.group = null;
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

    @JsonProperty("group")
    public Group getGroup() {
        return group;
    }

    public void setGroup(Group group) {
        this.group = group;
    }
}
