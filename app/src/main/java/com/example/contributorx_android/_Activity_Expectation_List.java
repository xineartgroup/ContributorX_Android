package com.example.contributorx_android;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import java.util.ArrayList;
import java.util.List;

public class _Activity_Expectation_List extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_expectation_list);

        // Set up toolbar
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        // Initialize views
        EditText searchValue = findViewById(R.id.searchValue);
        ImageButton btnSearch = findViewById(R.id.btnSearch);
        ListView lstDetail = findViewById(R.id.lstDetail);

        List<Expectation> expectations = _DAO_Expectation.GetAllExpectations();

        _Layout_Expectation_List0 iAdapter = new _Layout_Expectation_List0(this, expectations);
        lstDetail.setAdapter(iAdapter);

        /* btnSearch.setOnClickListener(v -> {
            String query = searchValue.getText().toString().trim();
            filterExpectations(expectations, query);
        });*/
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
}