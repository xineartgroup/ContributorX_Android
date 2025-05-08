package com.example.contributorx_android;

import com.fasterxml.jackson.databind.ObjectMapper;

public class _DAO_Expense {

    public static APIExpensesResponse GetAllExpenses() {
        String result = APIClass.SendMessage("GET", "group/api/all","", "", false);
        return APIClass.GetExpensesResponse(result);
    }

    public static APIExpensesResponse GetAllExpensesInCommunity(int communityId) {
        String result = APIClass.SendMessage("GET", "expense/api?communityid=" + communityId + String.format("&searchValue=%s&sortName=%s&sortOrder=%s", "*", "Id", "ASC"),"", "", false);
        return APIClass.GetExpensesResponse(result);
    }

    public static APIExpenseResponse AddExpense(Expense expense) {
        ObjectMapper objectMapper = new ObjectMapper();
        try {
            String jsonData = objectMapper.writeValueAsString(expense);
            String result = APIClass.SendMessage("POST", "expense/api/","", jsonData, false);
            return APIClass.GetExpenseResponse(result);
        } catch (Exception e) {
            android.util.Log.d("ERROR!!!", e.toString());
            return new APIExpenseResponse(e.getMessage());
        }
    }

    public static APIExpenseResponse UpdateExpense(Expense expense) {
        ObjectMapper objectMapper = new ObjectMapper();
        try {
            String jsonData = objectMapper.writeValueAsString(expense);
            String result = APIClass.SendMessage("POST", "expense/api/update/" + expense.getId(),"", jsonData, false);
            return APIClass.GetExpenseResponse(result);
        } catch (Exception e) {
            android.util.Log.d("ERROR!!!", e.toString());
            return new APIExpenseResponse(e.getMessage());
        }
    }

    public static APIExpenseResponse DeleteExpense(int id) {
        String result = APIClass.SendMessage("POST", "expense/api/delete/" + id,"", "", false);
        return APIClass.GetExpenseResponse(result);
    }

    public static APIExpenseResponse GetExpense(int id) {
        String result = APIClass.SendMessage("GET", "expense/api/" + id,"", "", false);
        return APIClass.GetExpenseResponse(result);
    }
}
