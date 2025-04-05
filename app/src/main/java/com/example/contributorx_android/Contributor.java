package com.example.contributorx_android;

import java.util.Calendar;
import java.util.Objects;

public class Contributor {
    private int id = 0;
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
    private Calendar startDate = Calendar.getInstance();

    public Contributor() {

    }

    public Contributor(String username, String firstname, String lastname, String role, String email, String phoneNumber, String picture, int communityId, boolean active, Calendar startDate) {
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

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getUserName() {
        return username;
    }

    public void setUserName(String number) {
        this.username = number;
    }

    public String getFirstname() {
        return firstname;
    }

    public void setFirstname(String firstname) {
        this.firstname = firstname;
    }

    public String getLastname() {
        return lastname;
    }

    public void setLastname(String lastname) {
        this.lastname = lastname;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public int getCommunityId() {
        return communityId;
    }

    public void setCommunityId(int communityId) {
        this.communityId = communityId;
    }

    public String getEmail() {
		return email;
	}

    public void setEmail(String email) {
		this.email = email;
	}

    public String getPhoneNumber() {
		return phoneNumber;
	}

    public void setPhoneNumber(String phoneNumber) {
		this.phoneNumber = phoneNumber;
	}

    public String getPicture() {
        return picture;
    }

    public void setPicture(String picture) {
        this.picture = picture;
    }

    public String getRole() {
        return role;
    }

    public void setAdmin(String role) {
        role = role;
    }

    public boolean isActive() {
        return active;
    }

    public void setActive(boolean active) {
        this.active = active;
    }

    public Calendar getStartDate() {
        return startDate;
    }

    public void setStartDate(Calendar startDate) {
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
