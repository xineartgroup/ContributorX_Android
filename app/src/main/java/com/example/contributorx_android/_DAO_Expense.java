package com.example.contributorx_android;

import java.util.ArrayList;
import java.util.List;

public class _DAO_Expense {

    private static int lastId = 0;

    private static List<Expense> expenses = new ArrayList<>();

    public static List<Expense> GetAllExpense() {
        return expenses;
    }

    public static int AddExpense(Expense expense) {
        expense.setId(++lastId);
        expenses.add(expense);
        return expense.getId();
    }

    public static void UpdateExpense(Expense expense) {
        for (int index = 0; index < expenses.size(); index++) {
            if (expenses.get(index).getId() == expense.getId()) {
                expenses.set(index, expense);
                return;
            }
        }
    }

    public static void DeleteExpense(int id) {
        for (int index = 0; index < expenses.size(); index++) {
            if (expenses.get(index).getId() == id) {
                expenses.remove(index);
                return;
            }
        }
    }

    public static Expense GetExpense(int id) {
        for (int index = 0; index < expenses.size(); index++) {
            if (expenses.get(index).getId() == id) {
                return expenses.get(index);
            }
        }
        return null;
    }
}
