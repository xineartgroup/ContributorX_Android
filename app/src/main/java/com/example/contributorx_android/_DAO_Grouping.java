package com.example.contributorx_android;

import com.fasterxml.jackson.databind.ObjectMapper;

public class _DAO_Grouping {

    public static APIResponse GetAllGrouping() {
        return APIClass.SendMessage("GET", "groupings/api/all","", false);
    }

    public static APIResponse DeleteGrouping(int id) {
        return APIClass.SendMessage("POST", "groupings/api/delete/" + id,"", false);
    }

    public static APIResponse GetGrouping(int id) {
        return APIClass.SendMessage("GET", "groupings/api/" + id,"", false);
    }

    public static APIResponse AddGrouping(Grouping grouping) {
        ObjectMapper objectMapper = new ObjectMapper();
        try {
            String jsonData = objectMapper.writeValueAsString(grouping);
            return APIClass.SendMessage("POST", "groupings/api/", jsonData, false);
        } catch (Exception e) {
            android.util.Log.d("ERROR!!!", e.toString());
            return new APIResponse(e.getMessage());
        }
    }

    public static APIResponse UpdateGrouping(Grouping grouping) {
        ObjectMapper objectMapper = new ObjectMapper();
        try {
            String jsonData = objectMapper.writeValueAsString(grouping);
            return APIClass.SendMessage("POST", "groupings/api/update/" + grouping.getId(), jsonData, false);
        } catch (Exception e) {
            android.util.Log.d("ERROR!!!", e.toString());
            return new APIResponse(e.getMessage());
        }
    }

    public static APIResponse GetGroupingsForContributor(int contributorId) {
        return APIClass.SendMessage("GET", "groupings/api/bycontributor/" + contributorId,"", false);
    }
}
