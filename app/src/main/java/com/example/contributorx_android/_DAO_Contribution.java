package com.example.contributorx_android;

import com.fasterxml.jackson.databind.ObjectMapper;

public class _DAO_Contribution {

    public static APIResponse GetAllContributions() {
        return APIClass.SendMessage("GET", "contributions/api/all","", false);
    }

    public static APIResponse GetAllContributionsInCommunity(int communityId) {
        return APIClass.SendMessage("GET", "contributions/api?communityid=" + communityId + String.format("&searchValue=%s&sortName=%s&sortOrder=%s", "*", "Id", "ASC"),"", false);
    }

    public static APIResponse AddContribution(Contribution contribution) {
        ObjectMapper objectMapper = new ObjectMapper();
        try {
            String jsonData = objectMapper.writeValueAsString(contribution);
            return APIClass.SendMessage("POST", "contributions/api/", jsonData, false);
        } catch (Exception e) {
            android.util.Log.d("ERROR!!!", e.toString());
            return new APIResponse(e.getMessage());
        }
    }

    public static APIResponse UpdateContribution(Contribution contribution) {
        ObjectMapper objectMapper = new ObjectMapper();
        try {
            String jsonData = objectMapper.writeValueAsString(contribution);
            return APIClass.SendMessage("POST", "contributions/api/update/" + contribution.getId(), jsonData, false);
        } catch (Exception e) {
            android.util.Log.d("ERROR!!!", e.toString());
            return new APIResponse(e.getMessage());
        }
    }

    public static APIResponse DeleteContribution(int id) {
        return APIClass.SendMessage("POST", "contributions/api/delete/" + id,"", false);
    }

    public static APIResponse GetContribution(int id) {
        return APIClass.SendMessage("GET", "contributions/api/" + id,"", false);
    }
}
