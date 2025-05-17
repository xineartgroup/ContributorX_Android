package com.example.contributorx_android;

import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.IOException;

public class _DAO_Auth {

    public static APIResponse Login(String username, String password) {
        Contributor user = new Contributor();
        user.setUserName(username);
        user.setPassword(password);
        ObjectMapper objectMapper = new ObjectMapper();
        try {
            String jsonData = objectMapper.writeValueAsString(user);
            return APIClass.SendMessage("POST", "auth/api/login", jsonData, true, 0);
        } catch (IOException e) {
            return new APIResponse(e.getMessage());
        }
    }

    public static APIResponse Register(String username, String password,
                                       String firstname, String lastname,
                                       String email, String role,
                                       String phonenumber, int communityid,
                                       String picture, boolean isactive) {
        Contributor user = new Contributor();
        user.setUserID(username);
        user.setUserName(username);
        user.setPassword(password);
        user.setFirstname(firstname);
        user.setLastname(lastname);
        user.setEmail(email);
        user.setRole(role);
        user.setPhoneNumber(phonenumber);
        user.setCommunityId(communityid);
        user.setPicture(picture);
        user.setActive(isactive);
        ObjectMapper objectMapper = new ObjectMapper();
        try {
            String jsonData = objectMapper.writeValueAsString(user);
            return APIClass.SendMessage("POST", "auth/api/register", jsonData, true, 0);
        } catch (IOException e) {
            return new APIResponse(e.getMessage());
        }
    }
}
