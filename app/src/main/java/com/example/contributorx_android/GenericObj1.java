package com.example.contributorx_android;

import com.fasterxml.jackson.annotation.JsonProperty;

public class GenericObj1 {
    private int id = 0;
    private boolean active = true;
    private String groups = "";
    private String passwordOld = "";
    private String PasswordNew = "";
    private String PasswordConfirm = "";

    @JsonProperty("id")
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    @JsonProperty("IsActive")
    public boolean isActive() {
        return active;
    }

    public void setActive(boolean active) {
        this.active = active;
    }

    @JsonProperty("groups")
    public String getGroups() {
        return groups;
    }

    public void setGroups(String groups) {
        this.groups = groups;
    }

    @JsonProperty("PasswordOld")
    public String getPasswordOld() {
        return passwordOld;
    }

    public void setPasswordOld(String passwordOld) {
        this.passwordOld = passwordOld;
    }

    @JsonProperty("PasswordNew")
    public String getPasswordNew() {
        return PasswordNew;
    }

    public void setPasswordNew(String passwordNew) {
        PasswordNew = passwordNew;
    }

    @JsonProperty("PasswordConfirm")
    public String getPasswordConfirm() {
        return PasswordConfirm;
    }

    public void setPasswordConfirm(String passwordConfirm) {
        PasswordConfirm = passwordConfirm;
    }
}
