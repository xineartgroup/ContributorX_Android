package com.example.contributorx_android;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

public class _Activity_Expense_Detail extends AppCompatActivity {
    Expense expense = null;

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

        if (_Activity_Login.LoggedOnUser == null){
            Toast.makeText(this, "Please log in first", Toast.LENGTH_LONG).show();
            Intent startIntent = new Intent(getApplicationContext(), _Activity_Login.class);
            startActivity(startIntent);
            finish();
            return;
        }

        if (intent.hasExtra("com.example.contributorx_android.ITEMINDEX")) {
            int id = intent.getIntExtra("com.example.contributorx_android.ITEMINDEX", -1);
            if (id >= 0) {
                expense = _DAO_Expense.GetExpense(id);

                if (expense != null) {
                    txtExpenseName.setText(expense.getName());
                    txtDescription.setText(expense.getDescription());
                    txtAmount.setText(String.format("%s", expense.getAmountPaid()));
                }
            }
        }

        btnSaveExpense.setOnClickListener(view -> {
            try {
                if (expense == null) {
                    expense = new Expense();

                    expense.setName(txtExpenseName.getText().toString());
                    expense.setDescription(txtDescription.getText().toString());
                    expense.setAmountPaid(Float.parseFloat(txtAmount.getText().toString().trim()));
                    expense.setCommunityId(_Activity_Login.LoggedOnUser.getCommunityId());

                    expense.setId(_DAO_Expense.AddExpense(expense));
                } else {
                    _DAO_Expense.UpdateExpense(expense);
                }

                Intent startIntent = new Intent(getApplicationContext(), _Activity_Expense_List.class);
                startActivity(startIntent);
            } catch (Exception e) {
                Toast.makeText(this, "Error saving: " + e.getMessage(), Toast.LENGTH_LONG).show();
            }
        });

        btnCancel.setOnClickListener(view -> {
            finish();
        });
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