package com.example.contributorx_android;

import com.fasterxml.jackson.databind.ObjectMapper;

public class _DAO_Expense {

    public static APIResponse GetAllExpenses() {
        return APIClass.SendMessage("GET", "expenses/api/all","", false);
    }

    public static APIResponse GetAllExpensesInCommunity(int communityId) {
        return APIClass.SendMessage("GET", "expenses/api?communityid=" + communityId + String.format("&searchValue=%s&sortName=%s&sortOrder=%s", "*", "Id", "ASC"),"", false);
    }

    public static APIResponse AddExpense(Expense expense) {
        ObjectMapper objectMapper = new ObjectMapper();
        try {
            String jsonData = objectMapper.writeValueAsString(expense);
            return APIClass.SendMessage("POST", "expenses/api/", jsonData, false);
        } catch (Exception e) {
            android.util.Log.d("ERROR!!!", e.toString());
            return new APIResponse(e.getMessage());
        }
    }

    public static APIResponse UpdateExpense(Expense expense) {
        ObjectMapper objectMapper = new ObjectMapper();
        try {
            String jsonData = objectMapper.writeValueAsString(expense);
            return APIClass.SendMessage("POST", "expenses/api/update/" + expense.getId(), jsonData, false);
        } catch (Exception e) {
            android.util.Log.d("ERROR!!!", e.toString());
            return new APIResponse(e.getMessage());
        }
    }

    public static APIResponse DeleteExpense(int id) {
        return APIClass.SendMessage("POST", "expenses/api/delete/" + id,"", false);
    }

    public static APIResponse GetExpense(int id) {
        return APIClass.SendMessage("GET", "expenses/api/" + id,"", false);
    }
}
