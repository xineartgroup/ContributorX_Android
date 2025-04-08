package com.example.contributorx_android;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ListView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import java.util.List;

public class _Activity_Expense_List extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_expense_list);

        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        Button btnAddExpense = findViewById(R.id.btnAddExpense);
        ListView lstDetail = findViewById(R.id.lstDetail);

        if (_Activity_Login.LoggedOnUser == null){
            Toast.makeText(this, "Please log in first", Toast.LENGTH_LONG).show();
            Intent startIntent = new Intent(getApplicationContext(), _Activity_Login.class);
            startActivity(startIntent);
            finish();
            return;
        }

        if (_Activity_Login.LoggedOnUser.getRole().equals("Administrator")) {
            btnAddExpense.setVisibility(View.VISIBLE);
        }
        else{
            btnAddExpense.setVisibility(View.GONE);
        }

        List<Expense> expenses = _DAO_Expense.GetAllExpense();

        _Layout_Expense_List iAdapter = new _Layout_Expense_List(this, expenses);
        lstDetail.setAdapter(iAdapter);

        lstDetail.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                if ("Administrator".equals(_Activity_Login.LoggedOnUser.getRole())) {
                    Expense expense = expenses.get(i);
                    if (expense != null) {
                        Intent startIntent = new Intent(getApplicationContext(), _Activity_Expense_Detail.class);
                        startIntent.putExtra("com.example.contributorx_android.ITEMINDEX", expense.getId());
                        startActivity(startIntent);
                    }
                }
            }
        });

        btnAddExpense.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent startIntent = new Intent(getApplicationContext(), _Activity_Expense_Detail.class);
                startActivity(startIntent);
            }
        });

    }
}