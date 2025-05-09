package com.example.contributorx_android;

import com.fasterxml.jackson.databind.ObjectMapper;

public class _DAO_Expectation {

    public static APIResponse GetAllExpectations() {
        String result = APIClass.SendMessage("GET", "expectation/api/all","", "", false);
        return APIClass.GetResponse(result);
    }

    public static APIResponse AddExpectation(Expectation expectation) {
        ObjectMapper objectMapper = new ObjectMapper();
        try {
            String jsonData = objectMapper.writeValueAsString(expectation);
            String result = APIClass.SendMessage("POST", "expectation/api/","", jsonData, false);
            return APIClass.GetResponse(result);
        } catch (Exception e) {
            return new APIResponse(e.getMessage());
        }
    }

    public static APIResponse UpdateExpectation(Expectation expectation) {
        ObjectMapper objectMapper = new ObjectMapper();
        try {
            String jsonData = objectMapper.writeValueAsString(expectation);
            String result = APIClass.SendMessage("POST", "expectation/api/update/" + expectation.getId(),"", jsonData, false);
            return APIClass.GetResponse(result);
        } catch (Exception e) {
            return new APIResponse(e.getMessage());
        }
    }

    public static APIResponse DeleteExpectation(int id) {
        String result = APIClass.SendMessage("POST", "expectation/api/delete/" + id,"", "", false);
        return APIClass.GetResponse(result);
    }

    public static APIResponse GetExpectation(int id) {
        String result = APIClass.SendMessage("GET", "expectation/api/" + id,"", "", false);
        return APIClass.GetResponse(result);
    }

    public static APIResponse GetExpectationsForContributor(int contributorId) {
        String result = APIClass.SendMessage("GET", "expectation/api/getbycontributor/" + contributorId + "/*","", "", false);
        return APIClass.GetResponse(result);
    }

    public static APIResponse GetUnclearedExpectationsInCommunity(int communityId) {
        String result = APIClass.SendMessage("GET", "expectation/api?communityid=" + communityId + String.format("&searchValue=%s&sortName=%s&sortOrder=%s", "*", "Id", "ASC"),"", "", false);
        return APIClass.GetResponse(result);
    }
}
