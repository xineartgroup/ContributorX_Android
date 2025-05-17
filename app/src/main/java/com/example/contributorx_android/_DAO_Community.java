package com.example.contributorx_android;

import com.fasterxml.jackson.databind.ObjectMapper;

public class _DAO_Community {

    public static APIResponse GetAllCommunities() {
        String result = APIClass.SendMessage("GET", "communities/api/all","", false);
        return APIClass.GetResponse(result);
    }

    public static APIResponse AddCommunity(Community community) {
        ObjectMapper objectMapper = new ObjectMapper();
        try {
            String jsonData = objectMapper.writeValueAsString(community);
            String result = APIClass.SendMessage("POST", "communities/api/", jsonData, false);
            return APIClass.GetResponse(result);
        } catch (Exception e) {
            android.util.Log.d("ERROR!!!", e.toString());
            return new APIResponse(e.getMessage());
        }
    }

    public static APIResponse UpdateCommunity(Community community) {
        ObjectMapper objectMapper = new ObjectMapper();
        try {
            String jsonData = objectMapper.writeValueAsString(community);
            String result = APIClass.SendMessage("POST", "communities/api/update/" + community.getId(), jsonData, false);
            return APIClass.GetResponse(result);
        } catch (Exception e) {
            android.util.Log.d("ERROR!!!", e.toString());
            return new APIResponse(e.getMessage());
        }
    }

    public static APIResponse DeleteCommunity(int id) {
        String result = APIClass.SendMessage("POST", "communities/api/delete/" + id,"", false);
        return APIClass.GetResponse(result);
    }

    public static APIResponse GetCommunity(int id) {
        String result = APIClass.SendMessage("GET", "communities/api/" + id,"", false);
        return APIClass.GetResponse(result);
    }

    public static APIResponse GetCommunityByName(String name) {
        String result = APIClass.SendMessage("GET", "communities/api/getbyname/" + name,"", false);
        return APIClass.GetResponse(result);
    }
}
