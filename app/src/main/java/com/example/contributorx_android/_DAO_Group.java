package com.example.contributorx_android;

import com.fasterxml.jackson.databind.ObjectMapper;

public class _DAO_Group {

    public static APIResponse GetAllGroups() {
        String result = APIClass.SendMessage("GET", "group/api/all","", false);
        return APIClass.GetResponse(result);
    }

    public static APIResponse GetAllGroupsInCommunity(int communityId) {
        String result = APIClass.SendMessage("GET", "group/api?communityid=" + communityId + String.format("&searchValue=%s&sortName=%s&sortOrder=%s", "*", "Id", "ASC"),"", false);
        return APIClass.GetResponse(result);
    }

    public static APIResponse AddGroup(Group group) {
        ObjectMapper objectMapper = new ObjectMapper();
        try {
            String jsonData = objectMapper.writeValueAsString(group);
            String result = APIClass.SendMessage("POST", "group/api/", jsonData, false);
            return APIClass.GetResponse(result);
        } catch (Exception e) {
            android.util.Log.d("ERROR!!!", e.toString());
            return new APIResponse(e.getMessage());
        }
    }

    public static APIResponse UpdateGroup(Group group) {
        ObjectMapper objectMapper = new ObjectMapper();
        try {
            String jsonData = objectMapper.writeValueAsString(group);
            String result = APIClass.SendMessage("POST", "group/api/update/" + group.getId(), jsonData, false);
            return APIClass.GetResponse(result);
        } catch (Exception e) {
            android.util.Log.d("ERROR!!!", e.toString());
            return new APIResponse(e.getMessage());
        }
    }

    public static APIResponse DeleteGroup(int id) {
        String result = APIClass.SendMessage("POST", "group/api/delete/" + id,"", false);
        return APIClass.GetResponse(result);
    }

    public static APIResponse GetGroup(int id) {
        String result = APIClass.SendMessage("GET", "group/api/" + id,"", false);
        return APIClass.GetResponse(result);
    }

    public static APIResponse GetGroupByName(String name) {
        String result = APIClass.SendMessage("GET", "group/api/getbyname/" + name,"", false);
        return APIClass.GetResponse(result);
    }
}
