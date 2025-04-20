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

        if (_Activity_Login.LoggedOnUser == null){
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
            String result = _DAO_Expectation.GetExpectationString(1);

            handler.post(() -> {
                // Update UI here with result
                Toast.makeText(this, result, Toast.LENGTH_LONG).show();
            });
        });

        List<Expectation> expectations = _DAO_Expectation.GetUnclearedExpectationsInCommunity(_Activity_Login.LoggedOnUser.getCommunityId());

        _Layout_Expectation_List0 iAdapter = new _Layout_Expectation_List0(this, expectations);
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
                iAdapter.expectations = Expectations(expectations, newText.trim());
                iAdapter.notifyDataSetChanged(); // Very important to refresh the view
                return true;
            }
        });
    }

    private List<Expectation> Expectations(List<Expectation> expectations, String query) {
        List<Expectation> result = new ArrayList<>();
        
        if (query.isEmpty()) {
            result.addAll(expectations);
        } else {
            query = query.toLowerCase();
            for (Expectation expectation : expectations) {
                Contributor contributor = _DAO_Contributor.GetContributor(expectation.getContributorId());
                Contribution contribution = _DAO_Contribution.GetContribution(expectation.getContributionId());

                if (contributor != null && contribution != null) {
                    // Filter by contributor name or contribution name
                    if (contributor.getUserName().toLowerCase().contains(query) ||
                            contribution.getName().toLowerCase().contains(query)) {
                        result.add(expectation);
                    }
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