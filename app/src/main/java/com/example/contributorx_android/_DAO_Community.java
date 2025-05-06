package com.example.contributorx_android;

import com.fasterxml.jackson.databind.ObjectMapper;

public class _DAO_Community {

    public static APICommunitiesResponse GetAllCommunities() {
        String result = APIClass.SendMessage("GET", "community/api/all","", "", false);
        return APIClass.GetCommunitiesResponse(result);
    }

    public static APICommunityResponse AddCommunity(Community community) {
        ObjectMapper objectMapper = new ObjectMapper();
        try {
            String jsonData = objectMapper.writeValueAsString(community);
            String result = APIClass.SendMessage("POST", "community/api/","", jsonData, false);
            return APIClass.GetCommunityResponse(result);
        } catch (Exception e) {
            android.util.Log.d("ERROR!!!", e.toString());
            return new APICommunityResponse(e.getMessage());
        }
    }

    public static APICommunityResponse UpdateCommunity(Community community) {
        ObjectMapper objectMapper = new ObjectMapper();
        try {
            String jsonData = objectMapper.writeValueAsString(community);
            String result = APIClass.SendMessage("POST", "community/api/update/" + community.getId(),"", jsonData, false);
            return APIClass.GetCommunityResponse(result);
        } catch (Exception e) {
            android.util.Log.d("ERROR!!!", e.toString());
            return new APICommunityResponse(e.getMessage());
        }
    }

    public static APICommunityResponse DeleteCommunity(int id) {
        String result = APIClass.SendMessage("POST", "community/api/delete/" + id,"", "", false);
        return APIClass.GetCommunityResponse(result);
    }

    public static APICommunityResponse GetCommunity(int id) {
        String result = APIClass.SendMessage("GET", "community/api/" + id,"", "", false);
        return APIClass.GetCommunityResponse(result);
    }
}
