package com.example.contributorx_android;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.IOException;

public class _DAO_Auth {

    public static APIContributorResponse Login(String username, String password) {
        User user = new User(username, password);
        ObjectMapper objectMapper = new ObjectMapper();
        try {
            String jsonData = objectMapper.writeValueAsString(user);
            System.out.println(jsonData);
            String result = APIClass.SendMessage("GET", "auth/api/login", "", jsonData, true);
            return APIClass.GetContributorResponse(result);
        } catch (IOException e) {
            return new APIContributorResponse(e.getMessage());
        }
    }

    public static class User {
        private String UserName;
        private String Password;

        User(String username, String password) {
            setUserName(username);
            setPassword(password);
        }

        @JsonProperty("UserName")
        public String getUserName() {
            return UserName;
        }

        public void setUserName(String userName) {
            UserName = userName;
        }

        @JsonProperty("Password")
        public String getPassword() {
            return Password;
        }

        public void setPassword(String password) {
            Password = password;
        }
    }
}
