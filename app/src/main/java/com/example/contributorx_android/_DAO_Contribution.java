package com.example.contributorx_android;

import com.fasterxml.jackson.databind.ObjectMapper;

public class _DAO_Contribution {

    public static APIContributionsResponse GetAllContributions() {
        String result = APIClass.SendMessage("GET", "contribution/api/all","", "", false);
        return APIClass.GetContributionsResponse(result);
    }

    public static APIContributionsResponse GetAllContributionsInCommunity(int communityId) {
        String result = APIClass.SendMessage("GET", "contribution/api?communityid=" + communityId + String.format("&searchValue=%s&sortName=%s&sortOrder=%s", "*", "Id", "ASC"),"", "", false);
        return APIClass.GetContributionsResponse(result);
    }

    public static APIContributionResponse AddContribution(Contribution contribution) {
        ObjectMapper objectMapper = new ObjectMapper();
        try {
            String jsonData = objectMapper.writeValueAsString(contribution);
            String result = APIClass.SendMessage("POST", "contribution/api/","", jsonData, false);
            return APIClass.GetContributionResponse(result);
        } catch (Exception e) {
            android.util.Log.d("ERROR!!!", e.toString());
            return new APIContributionResponse(e.getMessage());
        }
    }

    public static APIContributionResponse UpdateContribution(Contribution contribution) {
        ObjectMapper objectMapper = new ObjectMapper();
        try {
            String jsonData = objectMapper.writeValueAsString(contribution);
            String result = APIClass.SendMessage("POST", "contribution/api/update/" + contribution.getId(),"", jsonData, false);
            return APIClass.GetContributionResponse(result);
        } catch (Exception e) {
            android.util.Log.d("ERROR!!!", e.toString());
            return new APIContributionResponse(e.getMessage());
        }
    }

    public static APIContributionResponse DeleteContribution(int id) {
        String result = APIClass.SendMessage("POST", "contribution/api/delete/" + id,"", "", false);
        return APIClass.GetContributionResponse(result);
    }

    public static APIContributionResponse GetContribution(int id) {
        String result = APIClass.SendMessage("GET", "contribution/api/" + id,"", "", false);
        return APIClass.GetContributionResponse(result);
    }
}
