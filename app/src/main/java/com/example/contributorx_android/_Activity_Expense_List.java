package com.example.contributorx_android;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ListView;
import android.widget.SearchView;
import android.widget.Toast;
import androidx.activity.EdgeToEdge;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class _Activity_Expense_List extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_expense_list);

        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.expense_coordinator), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        Button btnAddExpense = findViewById(R.id.btnAddExpense);
        ListView lstDetail = findViewById(R.id.lstDetail);
        SearchView searchView = findViewById(R.id.searchView);

        if (APIClass.LoggedOnUser == null){
            Toast.makeText(this, "Please log in first", Toast.LENGTH_LONG).show();
            Intent startIntent = new Intent(getApplicationContext(), _Activity_Login.class);
            startActivity(startIntent);
            finish();
            return;
        }

        if (APIClass.LoggedOnUser.getRole().equals("Administrator")) {
            btnAddExpense.setVisibility(View.VISIBLE);
        }
        else{
            btnAddExpense.setVisibility(View.GONE);
        }

        ExecutorService executor = Executors.newSingleThreadExecutor();
        Handler handler = new Handler(Looper.getMainLooper());

        executor.execute(() -> {
            APIResponse response = _DAO_Expense.GetAllExpensesInCommunity(APIClass.LoggedOnUser.getCommunityId());

            handler.post(() -> {
                if (response.getIsSuccess()) {
                List<Expense> expenses = response.getExpenses();

                _Layout_Expense_List iAdapter = new _Layout_Expense_List(this, expenses);
                lstDetail.setAdapter(iAdapter);

                lstDetail.setOnItemClickListener((adapterView, view, i, l) -> {
                    if ("Administrator".equals(APIClass.LoggedOnUser.getRole())) {
                        Expense expense = expenses.get(i);
                        if (expense != null) {
                            Intent startIntent = new Intent(getApplicationContext(), _Activity_Expense_Detail.class);
                            startIntent.putExtra("com.example.contributorx_android.ITEMINDEX", expense.getId());
                            startActivity(startIntent);
                        }
                    }
                });

                btnAddExpense.setOnClickListener(view -> {
                    Intent startIntent = new Intent(getApplicationContext(), _Activity_Expense_Detail.class);
                    startActivity(startIntent);
                });

                searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
                    @Override
                    public boolean onQueryTextSubmit(String query) {
                        // Optional: handle action on submit, but not needed for filtering
                        return false;
                    }

                    @Override
                    public boolean onQueryTextChange(String newText) {
                        // Filter expenses and update adapter data
                        iAdapter.expenses = Expenses(expenses, newText.trim());
                        iAdapter.notifyDataSetChanged(); // Very important to refresh the view
                        return true;
                    }
                });
                }
            });
        });
    }

    private List<Expense> Expenses(List<Expense> expenses, String query) {
        List<Expense> result = new ArrayList<>();

        if (query.isEmpty()) {
            result.addAll(expenses);
        } else {
            query = query.toLowerCase();
            for (Expense expense : expenses) {
                // Filter by expense name or expense description
                if (expense.getDescription().toLowerCase().contains(query) ||
                        expense.getName().toLowerCase().contains(query)) {
                    result.add(expense);
                }
            }
        }

        return result;
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.mainmenu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        Intent startIntent = _Activity_Login.MenuAction(item.getItemId(), getApplicationContext());

        if (startIntent != null) {
            startActivity(startIntent);
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
}