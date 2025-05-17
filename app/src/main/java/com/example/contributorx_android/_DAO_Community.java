package com.example.contributorx_android;

import com.fasterxml.jackson.databind.ObjectMapper;

public class _DAO_Community {

    public static APIResponse GetAllCommunities() {
        return APIClass.SendMessage("GET", "communities/api/all","", false, 0);
    }

    public static APIResponse AddCommunity(Community community) {
        ObjectMapper objectMapper = new ObjectMapper();
        try {
            String jsonData = objectMapper.writeValueAsString(community);
            return APIClass.SendMessage("POST", "communities/api/", jsonData, false, 0);
        } catch (Exception e) {
            android.util.Log.d("ERROR!!!", e.toString());
            return new APIResponse(e.getMessage());
        }
    }

    public static APIResponse UpdateCommunity(Community community) {
        ObjectMapper objectMapper = new ObjectMapper();
        try {
            String jsonData = objectMapper.writeValueAsString(community);
            return APIClass.SendMessage("POST", "communities/api/update/" + community.getId(), jsonData, false, 0);
        } catch (Exception e) {
            android.util.Log.d("ERROR!!!", e.toString());
            return new APIResponse(e.getMessage());
        }
    }

    public static APIResponse DeleteCommunity(int id) {
        return APIClass.SendMessage("POST", "communities/api/delete/" + id,"", false, 0);
    }

    public static APIResponse GetCommunity(int id) {
        return APIClass.SendMessage("GET", "communities/api/" + id,"", false, 0);
    }

    public static APIResponse GetCommunityByName(String name) {
        return APIClass.SendMessage("GET", "communities/api/getbyname/" + name,"", false, 0);
    }
}
