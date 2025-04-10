package com.example.contributorx_android;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import java.util.Calendar;

public class _Activity_Login extends AppCompatActivity {

    public static Contributor LoggedOnUser = null;

    public static void PopulateDummyData()
    {
        Calendar date0 = Calendar.getInstance();
        date0.set(2025, 5, 31);
        Calendar date1 = Calendar.getInstance();
        date1.set(2025, 6, 31);

        _DAO_Community.AddCommunity(new Community("13 Akinsanya Residents", "Residents of 13 Akinsanya, Ajao, Isolo Lagos"));

        _DAO_Group.AddGroup(new Group("13 Akinsanya Residents Bills", "Bills for residents of 13 Akinsanya, Ajao, Isolo Lagos", 1));
        _DAO_Group.AddGroup(new Group("13 Akinsanya Social Contributions", "Contributions for residents of 13 Akinsanya, Ajao, Isolo Lagos", 1));
        _DAO_Group.AddGroup(new Group("13 Akinsanya Repairs", "Repairs for residents of 13 Akinsanya, Ajao, Isolo Lagos", 1));

        _DAO_Contributor.AddContributor(new Contributor("flat1", "Inaegwu", "Achichi", "Administrator", "x@x.com", "080", "img1.jpg", 1, true, date0));
        _DAO_Contributor.AddContributor(new Contributor("flat2", "Oluwafemi", "Akerele", "Contributor", "x@x.com", "080", "img2.jpg", 1, true, date0));
        _DAO_Contributor.AddContributor(new Contributor("flat3", "Seun", "Oresegun", "Contributor", "x@x.com", "080", "img3.jpg", 1, true, date0));
        _DAO_Contributor.AddContributor(new Contributor("flat4", "Obinna", "Chikwendu", "Contributor", "x@x.com", "080", "img4.jpg", 1, true, date0));
        _DAO_Contributor.AddContributor(new Contributor("flat5", "Mike", "Owen", "Contributor", "x@x.com", "080", "img5.jpg", 1, true, date0));
        _DAO_Contributor.AddContributor(new Contributor("Flat6", "Danny", "Chukwu", "Contributor", "x@x.com", "080", "img6.jpg", 1, true, date0));
        _DAO_Contributor.AddContributor(new Contributor("Flat7", "Emmanuel", "Nwokoma", "Contributor", "x@x.com", "080", "img7.jpg", 1, true, date0));
        _DAO_Contributor.AddContributor(new Contributor("Flat8", "Vincent", "Nwachukwu", "Contributor", "x@x.com", "080", "img8.jpg", 1, true, date0));
        _DAO_Contributor.AddContributor(new Contributor("Flat9", "Ay", "Dk", "Contributor", "x@x.com", "080", "img9.jpg", 1, false, date0));
        _DAO_Contributor.AddContributor(new Contributor("Flat10", "John", "Doe", "Contributor", "x@x.com", "080", "img10.jpg", 1, false, date0));
        _DAO_Contributor.AddContributor(new Contributor("flat11", "Adaeze", "Nburuka", "Contributor", "x@x.com", "080", "img11.jpg", 1, true, date0));
        _DAO_Contributor.AddContributor(new Contributor("flat12", "Anthony", "Mmadu", "Contributor", "x@x.com", "080", "img12.jpg", 1, true, date0));

        _DAO_Contribution.AddContribution(new Contribution("Monthly Dues for August", 4500, 1, date0, date1));
        _DAO_Contribution.AddContribution(new Contribution("Gate repair", 1250, 1, date0, date1));
        _DAO_Contribution.AddContribution(new Contribution("Monthly Dues for September", 4500, 1, date0, date1));

        _DAO_Expense.AddExpense(new Expense("Cleaning Expense For Jan 2025", "Cleaning Expense For Jan 2025", 4500, 1, ""));
        _DAO_Expense.AddExpense(new Expense("LAWMA Expense For Jan 2025", "LAWMA Expense For Jan 2025", 1250, 1, ""));
        _DAO_Expense.AddExpense(new Expense("Security Expense for Jan 2025", "Security Expense for Jan 2025", 4500, 1, ""));

        _DAO_Expectation.AddExpectation(new Expectation(1, 1, 0.00f, 0.00f, 0, ""));
        _DAO_Expectation.AddExpectation(new Expectation(1, 2, 0.00f, 0.00f, 0, ""));
        _DAO_Expectation.AddExpectation(new Expectation(1, 3, 0.00f, 0.00f, 0, ""));
        _DAO_Expectation.AddExpectation(new Expectation(2, 1, 4500, 0.00f, 0, ""));
        _DAO_Expectation.AddExpectation(new Expectation(2, 2, 1250, 0.00f, 0, ""));
        _DAO_Expectation.AddExpectation(new Expectation(2, 3, 0, 0.00f, 0, ""));
        _DAO_Expectation.AddExpectation(new Expectation(3, 1, 4500, 0.00f, 0, ""));
        _DAO_Expectation.AddExpectation(new Expectation(3, 2, 1250, 0.00f, 0, ""));
        _DAO_Expectation.AddExpectation(new Expectation(3, 3, 4500, 0.00f, 0, ""));

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

        Button btnLogin = (Button)findViewById(R.id.btnLogin);

        btnLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if (_DAO_Contributor.GetAllContributors().isEmpty()) {
                    PopulateDummyData();
                }

                EditText txtUsername = (EditText) findViewById(R.id.txtUsername);
                EditText txtPassword = (EditText) findViewById(R.id.txtPassword);

                LoggedOnUser = _DAO_Contributor.GetContributorByUsername(txtUsername.getText().toString());

                if (LoggedOnUser != null && LoggedOnUser.getPassword().equals(txtPassword.getText().toString())) {
                    Toast.makeText(_Activity_Login.this, "Login in " + txtUsername.getText().toString() + "...", Toast.LENGTH_LONG).show();

                    Intent startIntent = new Intent(getApplicationContext(), MainActivity.class);
                    startIntent.putExtra("MAIN_INFO_LOGINUSERNAME", txtUsername.getText().toString());
                    startActivity(startIntent);
                }
                else {
                    Toast.makeText(_Activity_Login.this, "Invalid username/password", Toast.LENGTH_LONG).show();
                }

                /*
                Intent browseUrl = new Intent(Intent.ACTION_VIEW, Uri.parse("www.google.com"));
                if (browseUrl.resolveActivity(getPackageManager()) != null) {
                    startActivity(browseUrl);
                }
                */
            }
        });
    }

    public static Intent MenuAction(int id, Context context) {
        if (id == android.R.id.home || id == R.id.itmHome) {
            return new Intent(context, MainActivity.class);
        } else if (id == R.id.itmContributors) {
            return new Intent(context, _Activity_Contributor_List.class);
        } else if (id == R.id.itmContributions) {
            return new Intent(context, _Activity_Contribution_List.class);
        } else if (id == R.id.itmReport) {
            Toast.makeText(context, "Reporting...", Toast.LENGTH_LONG).show();
            return null;
        } else if (id == R.id.itmExpenses) {
            return new Intent(context, _Activity_Expense_List.class);
        } else if (id == R.id.itmLogout) {
            _Activity_Login.LoggedOnUser = null;
            return new Intent(context, _Activity_Login.class);
        }/* else if (R.id.itmIndicatePayment) {
            Intent startIntent = new Intent(context, ContributorPaymentActivity.class);
            startIntent.putExtra("com.example.contributorx_android.ITEMINDEX", LoggedOnUser.getId());
            return startIntent;
        }*/

        return null;
    }
}