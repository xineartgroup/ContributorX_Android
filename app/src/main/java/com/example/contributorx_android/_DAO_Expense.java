package com.example.contributorx_android;

import com.fasterxml.jackson.databind.ObjectMapper;

public class _DAO_Expense {

    public static APIResponse GetAllExpenses() {
        String result = APIClass.SendMessage("GET", "expenses/api/all","", false);
        return APIClass.GetResponse(result);
    }

    public static APIResponse GetAllExpensesInCommunity(int communityId) {
        String result = APIClass.SendMessage("GET", "expenses/api?communityid=" + communityId + String.format("&searchValue=%s&sortName=%s&sortOrder=%s", "*", "Id", "ASC"),"", false);
        return APIClass.GetResponse(result);
    }

    public static APIResponse AddExpense(Expense expense) {
        ObjectMapper objectMapper = new ObjectMapper();
        try {
            String jsonData = objectMapper.writeValueAsString(expense);
            String result = APIClass.SendMessage("POST", "expenses/api/", jsonData, false);
            return APIClass.GetResponse(result);
        } catch (Exception e) {
            android.util.Log.d("ERROR!!!", e.toString());
            return new APIResponse(e.getMessage());
        }
    }

    public static APIResponse UpdateExpense(Expense expense) {
        ObjectMapper objectMapper = new ObjectMapper();
        try {
            String jsonData = objectMapper.writeValueAsString(expense);
            String result = APIClass.SendMessage("POST", "expenses/api/update/" + expense.getId(), jsonData, false);
            return APIClass.GetResponse(result);
        } catch (Exception e) {
            android.util.Log.d("ERROR!!!", e.toString());
            return new APIResponse(e.getMessage());
        }
    }

    public static APIResponse DeleteExpense(int id) {
        String result = APIClass.SendMessage("POST", "expenses/api/delete/" + id,"", false);
        return APIClass.GetResponse(result);
    }

    public static APIResponse GetExpense(int id) {
        String result = APIClass.SendMessage("GET", "expenses/api/" + id,"", false);
        return APIClass.GetResponse(result);
    }
}
