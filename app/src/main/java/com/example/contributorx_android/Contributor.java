package com.example.contributorx_android;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.Calendar;
import java.util.Objects;

public class Contributor {
    private int id = 0;
    private String userid = "";
    private String username = "";
    private String firstname = "";
    private String password = "";
    private String lastname = "";
    private String role = "";
    private String email = "";
    private String phoneNumber = "";
    private String picture = "";
    private int communityId;
    private boolean active = true;
    private String startDate = "";

    public Contributor() {

    }

    public Contributor(String username, String firstname, String lastname, String role, String email, String phoneNumber, String picture, int communityId, boolean active, String startDate) {
        this.password = "password";
        this.username = username;
        this.firstname = firstname;
        this.lastname = lastname;
        this.role = role;
        this.email = email;
        this.phoneNumber = phoneNumber;
        this.picture = picture;
        this.communityId = communityId;
        this.active = active;
        this.startDate = startDate;
    }

    @JsonProperty("Id")
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    @JsonProperty("UserID")
    public String getUserID() { return userid; }

    public void setUserID(String userID) { userid = userID; }

    @JsonProperty("UserName")
    public String getUserName() {
        return username;
    }

    public void setUserName(String number) {
        this.username = number;
    }

    @JsonProperty("FirstName")
    public String getFirstname() {
        return firstname;
    }

    public void setFirstname(String firstname) {
        this.firstname = firstname;
    }

    @JsonProperty("LastName")
    public String getLastname() {
        return lastname;
    }

    public void setLastname(String lastname) {
        this.lastname = lastname;
    }

    @JsonProperty("Password")
    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    @JsonProperty("CommunityId")
    public int getCommunityId() {
        return communityId;
    }

    public void setCommunityId(int communityId) {
        this.communityId = communityId;
    }

    @JsonProperty("Email")
    public String getEmail() {
		return email;
	}

    public void setEmail(String email) {
		this.email = email;
	}

    @JsonProperty("PhoneNumber")
    public String getPhoneNumber() {
		return phoneNumber;
	}

    public void setPhoneNumber(String phoneNumber) {
		this.phoneNumber = phoneNumber;
	}

    @JsonProperty("Picture")
    public String getPicture() {
        return picture;
    }

    public void setPicture(String picture) {
        this.picture = picture;
    }

    @JsonProperty("Role")
    public String getRole() {
        return role;
    }

    public void setAdmin(String role) {
        role = role;
    }

    @JsonProperty("IsActive")
    public boolean isActive() {
        return active;
    }

    public void setActive(boolean active) {
        this.active = active;
    }

    @JsonProperty("StartDate")
    public String getStartDate() {
        return startDate;
    }

    public void setStartDate(String startDate) {
        this.startDate = startDate;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Contributor contributor = (Contributor) o;
        return Objects.equals(username, contributor.username) && Objects.equals(firstname, contributor.firstname) && Objects.equals(lastname, contributor.lastname);
    }

    @Override
    public int hashCode() {
        return 0;
    }
}
