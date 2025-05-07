package com.example.contributorx_android;

import com.fasterxml.jackson.databind.ObjectMapper;

public class _DAO_Group {

    public static APIGroupsResponse GetAllGroups() {
        String result = APIClass.SendMessage("GET", "group/api/all","", "", false);
        return APIClass.GetGroupsResponse(result);
    }

    public static APIGroupsResponse GetAllGroupsInCommunity(int communityId) {
        String result = APIClass.SendMessage("GET", "group/api?communityid=" + communityId + String.format("&searchValue=%s&sortName=%s&sortOrder=%s", "*", "Id", "ASC"),"", "", false);
        return APIClass.GetGroupsResponse(result);
    }

    public static APIGroupResponse AddGroup(Group group) {
        ObjectMapper objectMapper = new ObjectMapper();
        try {
            String jsonData = objectMapper.writeValueAsString(group);
            String result = APIClass.SendMessage("POST", "group/api/","", jsonData, false);
            return APIClass.GetGroupResponse(result);
        } catch (Exception e) {
            android.util.Log.d("ERROR!!!", e.toString());
            return new APIGroupResponse(e.getMessage());
        }
    }

    public static APIGroupResponse UpdateGroup(Group group) {
        ObjectMapper objectMapper = new ObjectMapper();
        try {
            String jsonData = objectMapper.writeValueAsString(group);
            String result = APIClass.SendMessage("POST", "group/api/update/" + group.getId(),"", jsonData, false);
            return APIClass.GetGroupResponse(result);
        } catch (Exception e) {
            android.util.Log.d("ERROR!!!", e.toString());
            return new APIGroupResponse(e.getMessage());
        }
    }

    public static APIGroupResponse DeleteGroup(int id) {
        String result = APIClass.SendMessage("POST", "group/api/delete/" + id,"", "", false);
        return APIClass.GetGroupResponse(result);
    }

    public static APIGroupResponse GetGroup(int id) {
        String result = APIClass.SendMessage("GET", "group/api/" + id,"", "", false);
        return APIClass.GetGroupResponse(result);
    }

    public static APIGroupResponse GetGroupByName(String name) {
        String result = APIClass.SendMessage("GET", "group/api/getbyname/" + name,"", "", false);
        return APIClass.GetGroupResponse(result);
    }
}
