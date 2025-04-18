package com.example.contributorx_android;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.SearchView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

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
        SearchView searchView = findViewById(R.id.searchView);
        ListView lstDetail = findViewById(R.id.lstDetail);

        List<Expectation> expectations = _DAO_Expectation.GetUnclearedExpectationsIn(_Activity_Login.LoggedOnUser.getCommunityId());

        _Layout_Expectation_List0 iAdapter = new _Layout_Expectation_List0(this, expectations);
        lstDetail.setAdapter(iAdapter);

        searchView.setOnSearchClickListener(v -> {
            String query = searchView.getQuery().toString().trim();
            filterExpectations(expectations, query);
        });
    }

    private void filterExpectations(List<Expectation> expectations, String query) {
        List<Expectation> filteredList = new ArrayList<>();
        
        if (query.isEmpty()) {
            filteredList.addAll(expectations);
        } else {
            query = query.toLowerCase();
            for (Expectation expectation : expectations) {
                Contributor contributor = _DAO_Contributor.GetContributor(expectation.getContributorId());
                Contribution contribution = _DAO_Contribution.GetContribution(expectation.getContributionId());

                if (contributor != null && contribution != null) {
                    // Filter by contributor name or contribution name
                    if (contributor.getUserName().toLowerCase().contains(query) ||
                            contribution.getName().toLowerCase().contains(query)) {
                        filteredList.add(expectation);
                    }
                }
            }
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