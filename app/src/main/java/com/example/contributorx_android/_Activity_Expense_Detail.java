package com.example.contributorx_android;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class _Activity_Expense_Detail extends AppCompatActivity {
    Expense expense = null;
    private ExecutorService executor;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_expense_detail);

        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        Intent intent = getIntent();

        TextView txtExpenseName = findViewById(R.id.txtExpenseName);
        TextView txtDescription = findViewById(R.id.txtDescription);
        TextView txtAmount = findViewById(R.id.txtAmount);
        Button btnSaveExpense = findViewById(R.id.btnSaveExpense);
        Button btnCancel = findViewById(R.id.btnCancel);

        if (APIClass.LoggedOnUser == null){
            Toast.makeText(this, "Please log in first", Toast.LENGTH_LONG).show();
            Intent startIntent = new Intent(getApplicationContext(), _Activity_Login.class);
            startActivity(startIntent);
            finish();
            return;
        }

        executor = Executors.newSingleThreadExecutor(); // Initialize executor as a field
        Handler handler = new Handler(Looper.getMainLooper());

        if (intent.hasExtra("com.example.contributorx_android.ITEMINDEX")) {
            int id = intent.getIntExtra("com.example.contributorx_android.ITEMINDEX", -1);
            if (id >= 0) {
                executor.execute(() -> {
                    APIExpenseResponse expenseResponse = _DAO_Expense.GetExpense(id);

                    handler.post(() -> {
                        if (expenseResponse != null && expenseResponse.getIsSuccess()) {
                            expense = expenseResponse.getExpense();

                            if (expense != null) {
                                txtExpenseName.setText(expense.getName());
                                txtDescription.setText(expense.getDescription());
                                txtAmount.setText(String.format("%s", expense.getAmountPaid()));
                            }
                        } else {
                            Toast.makeText(this, "Error fetching expense details", Toast.LENGTH_SHORT).show();
                        }
                    });
                });
            }
        }

        btnSaveExpense.setOnClickListener(view -> {
            executor.execute(() -> {
                try {
                    boolean isNew = (expense == null);
                    Expense expenseToSave = (expense == null) ? new Expense() : expense;

                    expenseToSave.setName(txtExpenseName.getText().toString());
                    expenseToSave.setDescription(txtDescription.getText().toString());
                    expenseToSave.setAmountPaid(Float.parseFloat(txtAmount.getText().toString().trim()));
                    expenseToSave.setCommunityId(APIClass.LoggedOnUser.getCommunityId());

                    APIExpenseResponse expenseResponse;
                    if (isNew) {
                        expenseResponse = _DAO_Expense.AddExpense(expenseToSave);
                    } else {
                        expenseResponse = _DAO_Expense.UpdateExpense(expenseToSave);
                    }

                    handler.post(() -> {
                        if (expenseResponse != null && expenseResponse.getIsSuccess()) {
                            expense = expenseResponse.getExpense();
                            Toast.makeText(this, "Expense saved successfully", Toast.LENGTH_SHORT).show();
                            Intent startIntent = new Intent(getApplicationContext(), _Activity_Expense_List.class);
                            startActivity(startIntent);
                            finish();
                        } else {
                            Toast.makeText(this, "Error saving Expense", Toast.LENGTH_LONG).show();
                            if (expenseResponse != null && expenseResponse.getMessage() != null) {
                                Toast.makeText(this, expenseResponse.getMessage(), Toast.LENGTH_LONG).show();
                            }
                        }
                    });
                } catch (Exception e) {
                    Toast.makeText(this, "Error saving: " + e.getMessage(), Toast.LENGTH_LONG).show();
                }
            });
        });

        btnCancel.setOnClickListener(view -> {
            finish();
        });
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (executor != null && !executor.isShutdown()) {
            executor.shutdownNow(); // Or executor.shutdown()
        }
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