package com.example.contributorx_android;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ListView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import java.util.List;
import java.util.Objects;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class _Activity_My_Report extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_report);

        // Set up toolbar
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        Objects.requireNonNull(getSupportActionBar()).setDisplayHomeAsUpEnabled(true);

        if (APIClass.LoggedOnUser == null){
            Toast.makeText(this, "Please log in first", Toast.LENGTH_LONG).show();
            Intent startIntent = new Intent(getApplicationContext(), _Activity_Login.class);
            startActivity(startIntent);
            finish();
            return;
        }

        // Initialize views
        ListView lstDetail = findViewById(R.id.lstDetail);

        ExecutorService executor = Executors.newSingleThreadExecutor();
        Handler handler = new Handler(Looper.getMainLooper());

        executor.execute(() -> {
            APIResponse response = _DAO_Expectation.GetExpectationsForContributor(APIClass.LoggedOnUser.getId());

            handler.post(() -> {
                if (response.getIsSuccess()) {
                    if (response.getExpectations() != null) {
                        List<Expectation> list = response.getExpectations();

                        float totalPaid = 0.0f;
                        float totalOwed = 0.0f;

                        for (int i = 0; i < list.size(); i++) {
                            totalPaid += list.get(i).getAmountPaid();
                            totalOwed += list.get(i).getContribution().getAmount();
                        }

                        Contribution contribution = new Contribution();
                        contribution.setName("Total");
                        contribution.setAmount(totalOwed);

                        Expectation expectation = new Expectation();
                        expectation.setContribution(contribution);
                        expectation.setContributor(APIClass.LoggedOnUser);
                        expectation.setAmountPaid(totalPaid);
                        list.add(expectation);

                        _Layout_My_Report iAdapter = new _Layout_My_Report(this, list);
                        lstDetail.setAdapter(iAdapter);
                    }
                }
            });
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        if (APIClass.LoggedOnUser != null && Objects.equals(APIClass.LoggedOnUser.getRole(), "Administrator")) {
            getMenuInflater().inflate(R.menu.admin_menu, menu);
        }
        else {
            getMenuInflater().inflate(R.menu.user_menu, menu);
        }
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