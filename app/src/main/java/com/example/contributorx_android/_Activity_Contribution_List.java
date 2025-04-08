package com.example.contributorx_android;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ListView;
import android.widget.PopupMenu;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import java.util.List;

public class _Activity_Contribution_List extends AppCompatActivity implements PopupMenu.OnMenuItemClickListener, View.OnClickListener {

    int selectedIndex = -1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_contribution_list);

        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        Button btnAddContribution = findViewById(R.id.btnAddContribution);
        ListView lstDetail = findViewById(R.id.lstDetail);

        if (_Activity_Login.LoggedOnUser == null){
            Toast.makeText(this, "Please log in first", Toast.LENGTH_LONG).show();
            Intent startIntent = new Intent(getApplicationContext(), _Activity_Login.class);
            startActivity(startIntent);
            finish();
            return;
        }

        if (_Activity_Login.LoggedOnUser.getRole().equals("Administrator")) {
            btnAddContribution.setVisibility(View.VISIBLE);
        }
        else{
            btnAddContribution.setVisibility(View.GONE);
        }

        List<Contribution> contributions = _DAO_Contribution.GetAllContributions();

        _Layout_Contribution_List iAdapter = new _Layout_Contribution_List(this, contributions);
        lstDetail.setAdapter(iAdapter);

        btnAddContribution.setOnClickListener(_Activity_Contribution_List.this);

        lstDetail.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                if (_Activity_Login.LoggedOnUser.getRole().equals("Administrator")) {
                    Contribution contribution = contributions.get(i);
                    if (contribution != null){
                        selectedIndex = contribution.getId();
                        PopupMenu popup = new PopupMenu(_Activity_Contribution_List.this, view);
                        popup.setOnMenuItemClickListener(_Activity_Contribution_List.this);
                        popup.inflate(R.menu.contributionmenu);
                        popup.show();
                    }
                }
            }
        });

    }

    @Override
    public void onClick(View view) {
        Intent startIntent = new Intent(getApplicationContext(), _Activity_Contribution_Detail.class);
        startActivity(startIntent);
    }

    @Override
    public boolean onMenuItemClick(MenuItem item) {
        if (item.getItemId() == R.id.itmEditContribution) {
            Intent intent = new Intent(this, _Activity_Contribution_Detail.class);
            intent.putExtra("com.example.contributorx_android.ITEMINDEX", selectedIndex);
            startActivity(intent);
            return true;
        }
        else if (item.getItemId() == R.id.itmDeleteContribution) {
            Toast.makeText(this, "Deleting...", Toast.LENGTH_LONG).show();
            return true;
        }
        else {
            return false;
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