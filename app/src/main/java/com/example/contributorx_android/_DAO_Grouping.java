package com.example.contributorx_android;

import com.fasterxml.jackson.databind.ObjectMapper;

public class _DAO_Grouping {

    public static APIResponse GetAllGrouping() {
        String result = APIClass.SendMessage("GET", "groupings/api/all","", false);
        return APIClass.GetResponse(result);
    }

    public static APIResponse DeleteGrouping(int id) {
        String result = APIClass.SendMessage("POST", "groupings/api/delete/" + id,"", false);
        return APIClass.GetResponse(result);
    }

    public static APIResponse GetGrouping(int id) {
        String result = APIClass.SendMessage("GET", "groupings/api/" + id,"", false);
        return APIClass.GetResponse(result);
    }

    public static APIResponse AddGrouping(Grouping grouping) {
        ObjectMapper objectMapper = new ObjectMapper();
        try {
            String jsonData = objectMapper.writeValueAsString(grouping);
            String result = APIClass.SendMessage("POST", "groupings/api/", jsonData, false);
            return APIClass.GetResponse(result);
        } catch (Exception e) {
            android.util.Log.d("ERROR!!!", e.toString());
            return new APIResponse(e.getMessage());
        }
    }

    public static APIResponse UpdateGrouping(Grouping grouping) {
        ObjectMapper objectMapper = new ObjectMapper();
        try {
            String jsonData = objectMapper.writeValueAsString(grouping);
            String result = APIClass.SendMessage("POST", "groupings/api/update/" + grouping.getId(), jsonData, false);
            return APIClass.GetResponse(result);
        } catch (Exception e) {
            android.util.Log.d("ERROR!!!", e.toString());
            return new APIResponse(e.getMessage());
        }
    }

    public static APIResponse GetGroupingsForContributor(int contributorId) {
        String result = APIClass.SendMessage("GET", "groupings/api/bycontributor/" + contributorId,"", false);
        return APIClass.GetResponse(result);
    }
}
