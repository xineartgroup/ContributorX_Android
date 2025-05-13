package com.example.contributorx_android;

import com.fasterxml.jackson.databind.ObjectMapper;

public class _DAO_Contributor {

    public static APIResponse GetAllContributors() {
        String result = APIClass.SendMessage("GET", "contributor/api/all","", "", false);
        return APIClass.GetResponse(result);
    }

    public static APIResponse AddContributor(Contributor contributor) {
        ObjectMapper objectMapper = new ObjectMapper();
        try {
            String jsonData = objectMapper.writeValueAsString(contributor);
            String result = APIClass.SendMessage("POST", "contributor/api/","", jsonData, false);
            return APIClass.GetResponse(result);
        } catch (Exception e) {
            android.util.Log.d("ERROR!!!", e.toString());
            return new APIResponse(e.getMessage());
        }
    }

    public static APIResponse UpdateContributor(Contributor contributor) {
        ObjectMapper objectMapper = new ObjectMapper();
        try {
            String jsonData = objectMapper.writeValueAsString(contributor);
            String result = APIClass.SendMessage("POST", "contributor/api/update/" + contributor.getId(),"", jsonData, false);
            return APIClass.GetResponse(result);
        } catch (Exception e) {
            android.util.Log.d("ERROR!!!", e.toString());
            return new APIResponse(e.getMessage());
        }
    }

    public static APIResponse DeleteContributor(int id) {
        String result = APIClass.SendMessage("POST", "contributor/api/delete/" + id,"", "", false);
        return APIClass.GetResponse(result);
    }

    public static APIResponse GetContributor(int id) {
        String result = APIClass.SendMessage("GET", "contributor/api/" + id,"", "", false);
        return APIClass.GetResponse(result);
    }

    public static APIResponse GetContributorsInCommunity(int communityId) {
        String result = APIClass.SendMessage("GET", "contributor/api?communityid=" + communityId + String.format("&searchValue=%s&sortName=%s&sortOrder=%s", "*", "Id", "ASC"),"", "", false);
        return APIClass.GetResponse(result);
    }

    public static APIResponse UpdateContributor1(GenericObj1 obj1) {
        ObjectMapper objectMapper = new ObjectMapper();
        try {
            String jsonData = objectMapper.writeValueAsString(obj1);
            String result = APIClass.SendMessage("POST", "contributor/api/update1","", jsonData, false);
            return APIClass.GetResponse(result);
        } catch (Exception e) {
            android.util.Log.d("ERROR!!!", e.toString());
            return new APIResponse(e.getMessage());
        }
    }
}
