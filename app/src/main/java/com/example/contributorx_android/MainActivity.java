package com.example.contributorx_android;

import android.content.Intent;
import android.os.Bundle;
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

public class MainActivity extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_main);
        setTitle("Welcome");

        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        if (_Activity_Login.LoggedOnUser == null){
            Intent startIntent = new Intent(getApplicationContext(), _Activity_Login.class);
            startActivity(startIntent);
            finish();
        }

        ListView lstUserExpectation = findViewById(R.id.lstUserExpectation);
        List<Expectation> list = _DAO_Expectation.GetExpectationsForContributor(_Activity_Login.LoggedOnUser.getId());

        List<Expectation> expectations = new ArrayList<>();

        for (Expectation item : list){
            Contribution contribution = _DAO_Contribution.GetContribution(item.getContributionId());
            if (contribution != null) {
                if (contribution.getAmount() - (item.getAmountPaid() + item.getAmountToApprove()) > 0.0) {
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