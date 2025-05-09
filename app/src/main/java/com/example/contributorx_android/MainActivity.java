package com.example.contributorx_android;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ListView;
import android.view.Menu;
import android.view.MenuItem;
import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class MainActivity extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_main);
        setTitle("Welcome");

        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        if (APIClass.LoggedOnUser == null){
            Intent startIntent = new Intent(getApplicationContext(), _Activity_Login.class);
            startActivity(startIntent);
            finish();
        }

        ListView lstUserExpectation = findViewById(R.id.lstUserExpectation);

        ExecutorService executor = Executors.newSingleThreadExecutor();
        Handler handler = new Handler(Looper.getMainLooper());

        executor.execute(() -> {
            APIResponse response = _DAO_Expectation.GetExpectationsForContributor(APIClass.LoggedOnUser.getId());

            handler.post(() -> {
                if (response.getIsSuccess()) {
                    if (response.getExpectations() != null) {
                        List<Expectation> list = response.getExpectations();

                        List<Expectation> expectations = new ArrayList<>();

                        for (Expectation item : list){
                            if (item.getPaymentStatus() != 2 && item.getPaymentStatus() != 3) {
                                
                                if (item.getContribution() == null) {
                                    APIResponse contributionResponse = _DAO_Contribution.GetContribution(item.getContributionId());
                                    if (contributionResponse.getIsSuccess() && contributionResponse.getContribution() != null) {
                                        item.setContribution(contributionResponse.getContribution());
                                    }
                                }

                                if (item.getContribution().getAmount() - (item.getAmountPaid() + item.getAmountToApprove()) > 0.0) {
                                    expectations.add(item);
                                }
                            }
                        }

                        _Layout_Expectation_List1 expectationItemAdapter = new _Layout_Expectation_List1(this, expectations);
                        lstUserExpectation.setAdapter(expectationItemAdapter);

                        LayoutInflater inflater = LayoutInflater.from(this);
                        View itemView = inflater.inflate(R.layout.layout_expectation_list1, null);

                        /*ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
                            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
                            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
                            return insets;
                        });*/
                    }
                }
            });
        });

        executor.shutdown();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.mainmenu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        Intent startIntent = _Activity_Login.MenuAction(item.getItemId(), getApplicationContext());

        if (startIntent != null) {
            startActivity(startIntent);
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
}