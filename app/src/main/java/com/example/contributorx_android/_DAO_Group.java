package com.example.contributorx_android;

import com.fasterxml.jackson.databind.ObjectMapper;

public class _DAO_Group {

    public static APIResponse GetAllGroups() {
        return APIClass.SendMessage("GET", "groups/api/all","", false, 0);
    }

    public static APIResponse GetAllGroupsInCommunity(int communityId) {
        return APIClass.SendMessage("GET", "groups/api?communityid=" + communityId + String.format("&searchValue=%s&sortName=%s&sortOrder=%s", "*", "Id", "ASC"),"", false, 0);
    }

    public static APIResponse AddGroup(Group group) {
        ObjectMapper objectMapper = new ObjectMapper();
        try {
            String jsonData = objectMapper.writeValueAsString(group);
            return APIClass.SendMessage("POST", "groups/api/", jsonData, false, 0);
        } catch (Exception e) {
            android.util.Log.d("ERROR!!!", e.toString());
            return new APIResponse(e.getMessage());
        }
    }

    public static APIResponse UpdateGroup(Group group) {
        ObjectMapper objectMapper = new ObjectMapper();
        try {
            String jsonData = objectMapper.writeValueAsString(group);
            return APIClass.SendMessage("POST", "groups/api/update/" + group.getId(), jsonData, false, 0);
        } catch (Exception e) {
            android.util.Log.d("ERROR!!!", e.toString());
            return new APIResponse(e.getMessage());
        }
    }

    public static APIResponse DeleteGroup(int id) {
        return APIClass.SendMessage("POST", "groups/api/delete/" + id,"", false, 0);
    }

    public static APIResponse GetGroup(int id) {
        return APIClass.SendMessage("GET", "groups/api/" + id,"", false, 0);
    }

    public static APIResponse GetGroupByName(String name) {
        return APIClass.SendMessage("GET", "groups/api/getbyname/" + name,"", false, 0);
    }
}
