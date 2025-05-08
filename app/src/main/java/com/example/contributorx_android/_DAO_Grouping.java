package com.example.contributorx_android;

import com.fasterxml.jackson.databind.ObjectMapper;

public class _DAO_Grouping {

    public static APIGroupingsResponse GetAllGrouping() {
        String result = APIClass.SendMessage("GET", "group/api/all","", "", false);
        return APIClass.GetGroupingsResponse(result);
    }

    public static APIGroupingResponse DeleteGrouping(int id) {
        String result = APIClass.SendMessage("POST", "grouping/api/delete/" + id,"", "", false);
        return APIClass.GetGroupingResponse(result);
    }

    public static APIGroupingResponse GetGrouping(int id) {
        String result = APIClass.SendMessage("GET", "grouping/api/" + id,"", "", false);
        return APIClass.GetGroupingResponse(result);
    }

    public static APIGroupingResponse AddGrouping(Grouping grouping) {
        ObjectMapper objectMapper = new ObjectMapper();
        try {
            String jsonData = objectMapper.writeValueAsString(grouping);
            String result = APIClass.SendMessage("POST", "grouping/api/","", jsonData, false);
            return APIClass.GetGroupingResponse(result);
        } catch (Exception e) {
            android.util.Log.d("ERROR!!!", e.toString());
            return new APIGroupingResponse(e.getMessage());
        }
    }

    public static APIGroupingResponse UpdateGrouping(Grouping grouping) {
        ObjectMapper objectMapper = new ObjectMapper();
        try {
            String jsonData = objectMapper.writeValueAsString(grouping);
            String result = APIClass.SendMessage("POST", "grouping/api/update/" + grouping.getId(),"", jsonData, false);
            return APIClass.GetGroupingResponse(result);
        } catch (Exception e) {
            android.util.Log.d("ERROR!!!", e.toString());
            return new APIGroupingResponse(e.getMessage());
        }
    }

    public static APIGroupingsResponse GetGroupingsForContributor(int contributorId) {
        String result = APIClass.SendMessage("GET", "grouping/api/bycontributor/" + contributorId,"", "", false);
        return APIClass.GetGroupingsResponse(result);
    }
}
