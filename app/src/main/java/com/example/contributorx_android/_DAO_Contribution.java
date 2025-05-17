package com.example.contributorx_android;

import com.fasterxml.jackson.databind.ObjectMapper;

public class _DAO_Contribution {

    public static APIResponse GetAllContributions() {
        String result = APIClass.SendMessage("GET", "contributions/api/all","", false);
        return APIClass.GetResponse(result);
    }

    public static APIResponse GetAllContributionsInCommunity(int communityId) {
        String result = APIClass.SendMessage("GET", "contributions/api?communityid=" + communityId + String.format("&searchValue=%s&sortName=%s&sortOrder=%s", "*", "Id", "ASC"),"", false);
        return APIClass.GetResponse(result);
    }

    public static APIResponse AddContribution(Contribution contribution) {
        ObjectMapper objectMapper = new ObjectMapper();
        try {
            String jsonData = objectMapper.writeValueAsString(contribution);
            String result = APIClass.SendMessage("POST", "contributions/api/", jsonData, false);
            return APIClass.GetResponse(result);
        } catch (Exception e) {
            android.util.Log.d("ERROR!!!", e.toString());
            return new APIResponse(e.getMessage());
        }
    }

    public static APIResponse UpdateContribution(Contribution contribution) {
        ObjectMapper objectMapper = new ObjectMapper();
        try {
            String jsonData = objectMapper.writeValueAsString(contribution);
            String result = APIClass.SendMessage("POST", "contributions/api/update/" + contribution.getId(), jsonData, false);
            return APIClass.GetResponse(result);
        } catch (Exception e) {
            android.util.Log.d("ERROR!!!", e.toString());
            return new APIResponse(e.getMessage());
        }
    }

    public static APIResponse DeleteContribution(int id) {
        String result = APIClass.SendMessage("POST", "contributions/api/delete/" + id,"", false);
        return APIClass.GetResponse(result);
    }

    public static APIResponse GetContribution(int id) {
        String result = APIClass.SendMessage("GET", "contributions/api/" + id,"", false);
        return APIClass.GetResponse(result);
    }
}
