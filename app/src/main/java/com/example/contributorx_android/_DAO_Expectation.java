package com.example.contributorx_android;

import com.fasterxml.jackson.databind.ObjectMapper;

public class _DAO_Expectation {

    public static APIResponse GetAllExpectations() {
        return APIClass.SendMessage("GET", "expectations/api/all","", false);
    }

    public static APIResponse AddExpectation(Expectation expectation) {
        ObjectMapper objectMapper = new ObjectMapper();
        try {
            String jsonData = objectMapper.writeValueAsString(expectation);
            return APIClass.SendMessage("POST", "expectations/api/", jsonData, false);
        } catch (Exception e) {
            return new APIResponse(e.getMessage());
        }
    }

    public static APIResponse UpdateExpectation(Expectation expectation) {
        ObjectMapper objectMapper = new ObjectMapper();
        try {
            String jsonData = objectMapper.writeValueAsString(expectation);
            return APIClass.SendMessage("POST", "expectations/api/update/" + expectation.getId(), jsonData, false);
        } catch (Exception e) {
            return new APIResponse(e.getMessage());
        }
    }

    public static APIResponse DeleteExpectation(int id) {
        return APIClass.SendMessage("POST", "expectations/api/delete/" + id,"", false);
    }

    public static APIResponse GetExpectation(int id) {
        return APIClass.SendMessage("GET", "expectations/api/" + id,"", false);
    }

    public static APIResponse GetExpectationsForContributor(int contributorId) {
        return APIClass.SendMessage("GET", "expectations/api/getbycontributor/" + contributorId + "/*","", false);
    }

    public static APIResponse GetUnclearedExpectationsInCommunity(int communityId) {
        return APIClass.SendMessage("GET", "expectations/api?communityid=" + communityId + String.format("&searchValue=%s&sortName=%s&sortOrder=%s", "*", "Id", "ASC"),"", false);
    }
}
