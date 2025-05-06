package com.example.contributorx_android;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class _Activity_Community_Detail extends AppCompatActivity {
    Community community = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_community_detail);

        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        TextView txtCommunityName = findViewById(R.id.txtCommunityName);
        TextView txtDescription = findViewById(R.id.txtDescription);
        Button btnSaveCommunity = findViewById(R.id.btnSaveCommunity);
        Button btnCancel = findViewById(R.id.btnCancel);

        if (APIClass.LoggedOnUser == null){
            Toast.makeText(this, "Please log in first", Toast.LENGTH_LONG).show();
            Intent startIntent = new Intent(getApplicationContext(), _Activity_Login.class);
            startActivity(startIntent);
            finish();
            return;
        }

        ExecutorService executor = Executors.newSingleThreadExecutor();
        Handler handler = new Handler(Looper.getMainLooper());

        if (APIClass.LoggedOnUser.getCommunityId() >= 0) {

            executor.execute(() -> {
                APICommunityResponse response = _DAO_Community.GetCommunity(APIClass.LoggedOnUser.getCommunityId());

                handler.post(() -> {
                    if (response.getIssuccess()) {
                        if (response.getCommunity() != null) {
                            community = response.getCommunity();
                            txtCommunityName.setText(community.getName());
                            txtDescription.setText(community.getDescription());
                        }
                    }
                });
            });
        }

        btnSaveCommunity.setOnClickListener(view -> {
            try {
                if (community == null) {
                    executor.execute(() -> {
                        community = new Community();

                        community.setName(txtCommunityName.getText().toString());
                        community.setDescription(txtDescription.getText().toString());

                        APICommunityResponse response = _DAO_Community.AddCommunity(community);

                        handler.post(() -> {
                            if (response.getIssuccess()) {
                                if (response.getCommunity() != null) {
                                    community = response.getCommunity();
                                }
                            }
                        });
                    });
                } else {
                    executor.execute(() -> {
                        community.setName(txtCommunityName.getText().toString());
                        community.setDescription(txtDescription.getText().toString());

                        APICommunityResponse response = _DAO_Community.UpdateCommunity(community);

                        handler.post(() -> {
                            if (response.getIssuccess()) {
                                if (response.getCommunity() != null) {
                                    community = response.getCommunity();
                                }
                            }
                        });
                    });
                }

                Toast.makeText(this, "Community Updated", Toast.LENGTH_LONG).show();
            } catch (Exception e) {
                Toast.makeText(this, "Error saving: " + e.getMessage(), Toast.LENGTH_LONG).show();
            }
        });

        btnCancel.setOnClickListener(view -> {
            finish();
        });
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