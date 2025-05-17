package com.example.contributorx_android;

import com.fasterxml.jackson.databind.ObjectMapper;

public class _DAO_Contributor {

    public static APIResponse GetAllContributors() {
        return APIClass.SendMessage("GET", "contributors/api/all","", false);
    }

    public static APIResponse AddContributor(Contributor contributor) {
        ObjectMapper objectMapper = new ObjectMapper();
        try {
            String jsonData = objectMapper.writeValueAsString(contributor);
            return APIClass.SendMessage("POST", "contributors/api/", jsonData, false);
        } catch (Exception e) {
            android.util.Log.d("ERROR!!!", e.toString());
            return new APIResponse(e.getMessage());
        }
    }

    public static APIResponse UpdateContributor(Contributor contributor) {
        ObjectMapper objectMapper = new ObjectMapper();
        try {
            String jsonData = objectMapper.writeValueAsString(contributor);
            return APIClass.SendMessage("POST", "contributors/api/update/" + contributor.getId(), jsonData, false);
        } catch (Exception e) {
            android.util.Log.d("ERROR!!!", e.toString());
            return new APIResponse(e.getMessage());
        }
    }

    public static APIResponse DeleteContributor(int id) {
        return APIClass.SendMessage("POST", "contributors/api/delete/" + id,"", false);
    }

    public static APIResponse GetContributor(int id) {
        return APIClass.SendMessage("GET", "contributors/api/" + id,"", false);
    }

    public static APIResponse GetContributorsInCommunity(int communityId) {
        return APIClass.SendMessage("GET", "contributors/api?communityid=" + communityId + String.format("&searchValue=%s&sortName=%s&sortOrder=%s", "*", "Id", "ASC"),"", false);
    }

    public static APIResponse UpdateContributor1(GenericObj1 obj1) {
        ObjectMapper objectMapper = new ObjectMapper();
        try {
            String jsonData = objectMapper.writeValueAsString(obj1);
            return APIClass.SendMessage("POST", "contributors/api/update1", jsonData, false);
        } catch (Exception e) {
            android.util.Log.d("ERROR!!!", e.toString());
            return new APIResponse(e.getMessage());
        }
    }

    public static APIResponse ChangePassword(GenericObj1 obj1) {
        ObjectMapper objectMapper = new ObjectMapper();
        try {
            String jsonData = objectMapper.writeValueAsString(obj1);
            return APIClass.SendMessage("POST", "contributors/api/changepassword/" + obj1.getId(), jsonData, false);
        } catch (Exception e) {
            android.util.Log.d("ERROR!!!", e.toString());
            return new APIResponse(e.getMessage());
        }
    }
}
