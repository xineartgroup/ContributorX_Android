package com.example.contributorx_android;

import com.fasterxml.jackson.databind.ObjectMapper;

public class _DAO_Expectation {

    public static APIExpectationsResponse GetAllExpectations() {
        String result = APIClass.SendMessage("GET", "expectation/api/all","", "", false);
        return APIClass.GetExpectationsResponse(result);
    }

    public static APIExpectationResponse AddExpectation(Expectation expectation) {
        ObjectMapper objectMapper = new ObjectMapper();
        try {
            String jsonData = objectMapper.writeValueAsString(expectation);
            String result = APIClass.SendMessage("POST", "expectation/api/","", jsonData, false);
            return APIClass.GetExpectationResponse(result);
        } catch (Exception e) {
            return new APIExpectationResponse(e.getMessage());
        }
    }

    public static APIExpectationResponse UpdateExpectation(Expectation expectation) {
        ObjectMapper objectMapper = new ObjectMapper();
        try {
            String jsonData = objectMapper.writeValueAsString(expectation);
            String result = APIClass.SendMessage("POST", "expectation/api/update/" + expectation.getId(),"", jsonData, false);
            return APIClass.GetExpectationResponse(result);
        } catch (Exception e) {
            return new APIExpectationResponse(e.getMessage());
        }
    }

    public static APIExpectationResponse DeleteExpectation(int id) {
        String result = APIClass.SendMessage("POST", "expectation/api/delete/" + id,"", "", false);
        return APIClass.GetExpectationResponse(result);
    }

    public static APIExpectationResponse GetExpectation(int id) {
        String result = APIClass.SendMessage("GET", "expectation/api/" + id,"", "", false);
        return APIClass.GetExpectationResponse(result);
    }

    public static APIExpectationsResponse GetExpectationsForContributor(int contributorId) {
        String result = APIClass.SendMessage("GET", "expectation/api/getbycontributor/" + contributorId + "/*","", "", false);
        return APIClass.GetExpectationsResponse(result);
    }

    public static APIExpectationsResponse GetUnclearedExpectationsInCommunity(int communityId) {
        String result = APIClass.SendMessage("GET", "expectation/api?communityid=" + communityId + String.format("&searchValue=%s&sortName=%s&sortOrder=%s", "*", "Id", "ASC"),"", "", false);
        return APIClass.GetExpectationsResponse(result);
    }
}
