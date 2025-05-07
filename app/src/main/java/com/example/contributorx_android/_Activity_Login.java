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

import java.util.Calendar;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class _Activity_Login extends AppCompatActivity {

    public static void PopulateDummyData()
    {
        Calendar date0 = Calendar.getInstance();
        date0.set(2025, 5, 31);
        Calendar date1 = Calendar.getInstance();
        date1.set(2025, 6, 31);

        //_DAO_Community.AddCommunity(new Community("13 Akinsanya Residents", "Residents of 13 Akinsanya, Ajao, Isolo Lagos"));

        _DAO_Group.AddGroup(new Group("13 Akinsanya Residents Bills", "Bills for residents of 13 Akinsanya, Ajao, Isolo Lagos", 1));
        _DAO_Group.AddGroup(new Group("13 Akinsanya Social Contributions", "Contributions for residents of 13 Akinsanya, Ajao, Isolo Lagos", 1));
        _DAO_Group.AddGroup(new Group("13 Akinsanya Repairs", "Repairs for residents of 13 Akinsanya, Ajao, Isolo Lagos", 1));

        //_DAO_Contributor.AddContributor(new Contributor("flat1", "Inaegwu", "Achichi", "Administrator", "x@x.com", "080", "img1.jpg", 1, true, date0.getTime().toString()));
        //_DAO_Contributor.AddContributor(new Contributor("flat2", "Oluwafemi", "Akerele", "Contributor", "x@x.com", "080", "img2.jpg", 1, true, date0.getTime().toString()));
        //_DAO_Contributor.AddContributor(new Contributor("flat3", "Seun", "Oresegun", "Contributor", "x@x.com", "080", "img3.jpg", 1, true, date0.getTime().toString()));
        //_DAO_Contributor.AddContributor(new Contributor("flat4", "Obinna", "Chikwendu", "Contributor", "x@x.com", "080", "img4.jpg", 1, true, date0.getTime().toString()));
        //_DAO_Contributor.AddContributor(new Contributor("flat5", "Mike", "Owen", "Contributor", "x@x.com", "080", "img5.jpg", 1, true, date0.getTime().toString()));
        //_DAO_Contributor.AddContributor(new Contributor("Flat6", "Danny", "Chukwu", "Contributor", "x@x.com", "080", "img6.jpg", 1, true, date0.getTime().toString()));
        //_DAO_Contributor.AddContributor(new Contributor("Flat7", "Emmanuel", "Nwokoma", "Contributor", "x@x.com", "080", "img7.jpg", 1, true, date0.getTime().toString()));
        //_DAO_Contributor.AddContributor(new Contributor("Flat8", "Vincent", "Nwachukwu", "Contributor", "x@x.com", "080", "img8.jpg", 1, true, date0.getTime().toString()));
        //_DAO_Contributor.AddContributor(new Contributor("Flat9", "Ay", "Dk", "Contributor", "x@x.com", "080", "img9.jpg", 1, false, date0.getTime().toString()));
        //_DAO_Contributor.AddContributor(new Contributor("Flat10", "John", "Doe", "Contributor", "x@x.com", "080", "img10.jpg", 1, false, date0.getTime().toString()));
        //_DAO_Contributor.AddContributor(new Contributor("flat11", "Adaeze", "Nburuka", "Contributor", "x@x.com", "080", "img11.jpg", 1, true, date0.getTime().toString()));
        //_DAO_Contributor.AddContributor(new Contributor("flat12", "Anthony", "Mmadu", "Contributor", "x@x.com", "080", "img12.jpg", 1, true, date0.getTime().toString()));

        //_DAO_Contribution.AddContribution(new Contribution("Monthly Dues for August", 4500, 1, date0.toString(), date1.toString()));
        //_DAO_Contribution.AddContribution(new Contribution("Gate repair", 1250, 1, date0.toString(), date1.toString()));
        //_DAO_Contribution.AddContribution(new Contribution("Monthly Dues for September", 3500, 1, date0.toString(), date1.toString()));

        _DAO_Expense.AddExpense(new Expense("Cleaning Expense For Jan 2025", "Cleaning Expense For Jan 2025", 4500, 1, ""));
        _DAO_Expense.AddExpense(new Expense("LAWMA Expense For Jan 2025", "LAWMA Expense For Jan 2025", 1250, 1, ""));
        _DAO_Expense.AddExpense(new Expense("Security Expense for Jan 2025", "Security Expense for Jan 2025", 4500, 1, ""));

        //_DAO_Expectation.AddExpectation(new Expectation(1, 1, 0.00f, 0.00f, 0, ""));
        //_DAO_Expectation.AddExpectation(new Expectation(1, 2, 0.00f, 0.00f, 0, ""));
        //_DAO_Expectation.AddExpectation(new Expectation(1, 3, 0.00f, 0.00f, 0, ""));
        //_DAO_Expectation.AddExpectation(new Expectation(2, 1, 4500, 0.00f, 0, ""));
        //_DAO_Expectation.AddExpectation(new Expectation(2, 2, 0.00f, 1250, 0, ""));
        //_DAO_Expectation.AddExpectation(new Expectation(2, 3, 1500, 0.00f, 0, ""));
        //_DAO_Expectation.AddExpectation(new Expectation(3, 1, 0.00f, 0.00f, 0, ""));
        //_DAO_Expectation.AddExpectation(new Expectation(3, 2, 0.00f, 0.00f, 0, ""));
        //_DAO_Expectation.AddExpectation(new Expectation(3, 3, 0.00f, 0.00f, 0, ""));

        _DAO_Grouping.AddGrouping(new Grouping(1, 1));
        _DAO_Grouping.AddGrouping(new Grouping(2, 1));
        _DAO_Grouping.AddGrouping(new Grouping(3, 1));
        _DAO_Grouping.AddGrouping(new Grouping(4, 1));
        _DAO_Grouping.AddGrouping(new Grouping(1, 2));
        _DAO_Grouping.AddGrouping(new Grouping(2, 2));
        _DAO_Grouping.AddGrouping(new Grouping(3, 2));
        _DAO_Grouping.AddGrouping(new Grouping(4, 2));
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        Button btnLogin = findViewById(R.id.btnLogin);

        btnLogin.setOnClickListener(view -> {

            //if (_DAO_Contributor.GetAllContributors().isEmpty()) {
            //    PopulateDummyData();
            //}

            EditText txtUsername = findViewById(R.id.txtUsername);
            EditText txtPassword = findViewById(R.id.txtPassword);

            ExecutorService executor = Executors.newSingleThreadExecutor();
            Handler handler = new Handler(Looper.getMainLooper());

            executor.execute(() -> {
                APIContributorResponse response = _DAO_Auth.Login(txtUsername.getText().toString(), txtPassword.getText().toString());

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