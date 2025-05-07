package com.example.contributorx_android;

import com.fasterxml.jackson.databind.ObjectMapper;

public class _DAO_Contributor {

    public static APIContributorsResponse GetAllContributors() {
        String result = APIClass.SendMessage("GET", "contributor/api/all","", "", false);
        return APIClass.GetContributorsResponse(result);
    }

    public static APIContributorResponse AddContributor(Contributor contributor) {
        ObjectMapper objectMapper = new ObjectMapper();
        try {
            String jsonData = objectMapper.writeValueAsString(contributor);
            String result = APIClass.SendMessage("POST", "contributor/api/","", jsonData, false);
            return APIClass.GetContributorResponse(result);
        } catch (Exception e) {
            android.util.Log.d("ERROR!!!", e.toString());
            return new APIContributorResponse(e.getMessage());
        }
    }

    public static APIContributorResponse UpdateContributor(Contributor contributor) {
        ObjectMapper objectMapper = new ObjectMapper();
        try {
            String jsonData = objectMapper.writeValueAsString(contributor);
            String result = APIClass.SendMessage("POST", "community/api/update/" + contributor.getId(),"", jsonData, false);
            return APIClass.GetContributorResponse(result);
        } catch (Exception e) {
            android.util.Log.d("ERROR!!!", e.toString());
            return new APIContributorResponse(e.getMessage());
        }
    }

    public static APIContributorResponse DeleteContributor(int id) {
        String result = APIClass.SendMessage("POST", "contributor/api/delete/" + id,"", "", false);
        return APIClass.GetContributorResponse(result);
    }

    public static APIContributorResponse GetContributor(int id) {
        String result = APIClass.SendMessage("GET", "contributor/api/" + id,"", "", false);
        return APIClass.GetContributorResponse(result);
    }

    public static APIContributorsResponse GetContributorsInCommunity(int communityId) {
        String result = APIClass.SendMessage("GET", "contributor/api?communityid=" + communityId + String.format("&searchValue=%s&sortName=%s&sortOrder=%s", "*", "Id", "ASC"),"", "", false);
        return APIClass.GetContributorsResponse(result);
    }
}
