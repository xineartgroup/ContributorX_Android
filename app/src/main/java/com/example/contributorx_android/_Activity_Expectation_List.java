package com.example.contributorx_android;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.ListView;
import android.widget.SearchView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class _Activity_Expectation_List extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_expectation_list);

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
        SearchView searchView = findViewById(R.id.searchView);

        ExecutorService executor = Executors.newSingleThreadExecutor();
        Handler handler = new Handler(Looper.getMainLooper());

        executor.execute(() -> {
            APIResponse response = _DAO_Expectation.GetUnclearedExpectationsInCommunity(APIClass.LoggedOnUser.getCommunityId());

            handler.post(() -> {
                if (response.getIsSuccess()) {
                    if (response.getExpectations() != null) {
                        List<Expectation> list = response.getExpectations();

                        /*List<Expectation> expectations = new ArrayList<>();

                        for (int i = 0; i < list.size(); i++) {
                            Expectation expectation = expectations.get(i);
                            Contributor contributor = _DAO_Contributor.GetContributor(expectation.getContributorId());
                            Contribution contribution = _DAO_Contribution.GetContribution(expectation.getContributionId());
                            if (contributor != null && contribution != null) {
                                if (expectation.getPaymentStatus() != 2 && expectation.getPaymentStatus() != 3 &&
                                        contribution.getAmount() - expectation.getAmountPaid() > 0) {
                                    expectations.add(expectation);
                                }
                            }
                        }*/

                        _Layout_Expectation_List0 iAdapter = new _Layout_Expectation_List0(this, list);
                        lstDetail.setAdapter(iAdapter);

                        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
                            @Override
                            public boolean onQueryTextSubmit(String query) {
                                // Optional: handle action on submit, but not needed for filtering
                                return false;
                            }

                            @Override
                            public boolean onQueryTextChange(String newText) {
                                // Filter expectations and update adapter data
                                iAdapter.expectations = Expectations(list, newText.trim());
                                iAdapter.notifyDataSetChanged(); // Very important to refresh the view
                                return true;
                            }
                        });
                    }
                }
            });
        });
    }

    private List<Expectation> Expectations(List<Expectation> expectations, String query) {
        List<Expectation> result = new ArrayList<>();
        
        if (query.isEmpty()) {
            result.addAll(expectations);
        } else {
            query = query.toLowerCase();
            for (Expectation expectation : expectations) {

                ExecutorService executor = Executors.newSingleThreadExecutor();
                Handler handler = new Handler(Looper.getMainLooper());

                String finalQuery = query;
                executor.execute(() -> {
                    APIResponse response = _DAO_Contributor.GetContributor(expectation.getContributorId());

                    handler.post(() -> {
                        if (response.getIsSuccess()) {
                            Contributor contributor = response.getContributor();

                            if (expectation.getContribution() == null) {
                                APIResponse contributionResponse = _DAO_Contribution.GetContribution(expectation.getContributionId());
                                if (contributionResponse.getIsSuccess() && contributionResponse.getContribution() != null) {
                                    expectation.setContribution(contributionResponse.getContribution());
                                }
                            }

                            if (contributor != null && expectation.getContribution() != null) {
                                // Filter by contributor name or contribution name
                                if (contributor.getUserName().toLowerCase().contains(finalQuery) ||
                                        expectation.getContribution().getName().toLowerCase().contains(finalQuery)) {
                                    result.add(expectation);
                                }
                            }
                        }
                    });
                });

                executor.shutdown();
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