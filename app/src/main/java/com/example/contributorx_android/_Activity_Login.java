package com.example.contributorx_android;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class _Activity_Login extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        Button btnLogin = findViewById(R.id.btnLogin);

        btnLogin.setOnClickListener(view -> {

            EditText txtUsername = findViewById(R.id.txtUsername);
            EditText txtPassword = findViewById(R.id.txtPassword);

            ExecutorService executor = Executors.newSingleThreadExecutor();
            Handler handler = new Handler(Looper.getMainLooper());

            executor.execute(() -> {
                APIResponse response = _DAO_Auth.Login(txtUsername.getText().toString(), txtPassword.getText().toString());

                handler.post(() -> {
                    if (response != null && response.getIsSuccess() && response.getContributor() != null) {
                        APIClass.LoggedOnUser = response.getContributor();
                        Intent startIntent = new Intent(getApplicationContext(), MainActivity.class);
                        startIntent.putExtra("MAIN_INFO_LOGINUSERNAME", txtUsername.getText().toString());
                        startActivity(startIntent);
                        Toast.makeText(_Activity_Login.this, "Logged in " + APIClass.LoggedOnUser.getUserName(), Toast.LENGTH_LONG).show();
                    }
                    else {
                        APIClass.LoggedOnUser = null;
                        Toast.makeText(_Activity_Login.this, "Invalid username/password", Toast.LENGTH_LONG).show();
                    }
                });
            });
        });
    }

    public static Intent MenuAction(int id, Context context) {
        if (id == android.R.id.home || id == R.id.itmHome) {
            return new Intent(context, MainActivity.class);
        } else if (id == R.id.itmCommunity) {
            return new Intent(context, _Activity_Community_Detail.class);
        } else if (id == R.id.itmExpectations) {
            return new Intent(context, _Activity_Expectation_List.class);
        } else if (id == R.id.itmContributors) {
            return new Intent(context, _Activity_Contributor_List.class);
        } else if (id == R.id.itmContributions) {
            return new Intent(context, _Activity_Contribution_List.class);
        } else if (id == R.id.itmExpenses) {
            return new Intent(context, _Activity_Expense_List.class);
        } else if (id == R.id.itmGroups) {
            return new Intent(context, _Activity_Group_List.class);
        } else if (id == R.id.itmLogout) {
            APIClass.LoggedOnUser = null;
            return new Intent(context, _Activity_Login.class);
        }/*  else if (id == R.id.itmReport) {
            Toast.makeText(context, "Reporting...", Toast.LENGTH_LONG).show();
            return null;
        }*/

        return null;
    }
}