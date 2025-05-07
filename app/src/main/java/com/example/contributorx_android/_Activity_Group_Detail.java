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

public class _Activity_Group_Detail extends AppCompatActivity {
    Group group;
    private TextView txtGroupName;
    private TextView txtDescription;
    private Button btnSaveGroup;
    private ExecutorService executor; // Declare executor as a field

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_group_detail);

        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        Intent intent = getIntent();

        txtGroupName = findViewById(R.id.txtGroupName);
        txtDescription = findViewById(R.id.txtDescription);
        btnSaveGroup = findViewById(R.id.btnSaveGroup);
        Button btnCancel = findViewById(R.id.btnCancel);

        if (APIClass.LoggedOnUser == null) {
            Toast.makeText(this, "Please log in first", Toast.LENGTH_LONG).show();
            Intent startIntent = new Intent(getApplicationContext(), _Activity_Login.class);
            startActivity(startIntent);
            finish();
            return;
        }

        executor = Executors.newSingleThreadExecutor(); // Initialize executor as a field
        Handler handler = new Handler(Looper.getMainLooper());

        // Fetch group details if ID is present
        if (intent.hasExtra("com.example.contributorx_android.ITEMINDEX")) {
            int id = intent.getIntExtra("com.example.contributorx_android.ITEMINDEX", -1);
            if (id >= 0) {
                executor.execute(() -> {
                    APIGroupResponse response = _DAO_Group.GetGroup(id);

                    handler.post(() -> {
                        if (response != null && response.getIsSuccess()) {
                            group = response.getGroup();
                            if (group != null) {
                                txtGroupName.setText(group.getName());
                                txtDescription.setText(group.getDescription());
                            }
                        } else {
                            Toast.makeText(this, "Error fetching group details", Toast.LENGTH_SHORT).show();
                        }
                    });
                });
            }
        }

        // Set OnClickListener for Save button
        btnSaveGroup.setOnClickListener(view -> {
            executor.execute(() -> { // Perform save operation on a background thread
                try {
                    boolean isNew = (group == null);
                    Group groupToSave = (group == null) ? new Group() : group;

                    groupToSave.setName(txtGroupName.getText().toString());
                    groupToSave.setDescription(txtDescription.getText().toString());
                    groupToSave.setCommunityId(APIClass.LoggedOnUser.getCommunityId());

                    APIGroupResponse groupResponse;
                    if (isNew) {
                        groupResponse = _DAO_Group.AddGroup(groupToSave);
                    } else {
                        groupResponse = _DAO_Group.UpdateGroup(groupToSave);
                    }

                    handler.post(() -> {
                        if (groupResponse != null && groupResponse.getIsSuccess()) {
                            group = groupResponse.getGroup(); // Update the local group object
                            Toast.makeText(this, "Group saved successfully", Toast.LENGTH_SHORT).show();
                            Intent startIntent = new Intent(getApplicationContext(), _Activity_Group_List.class);
                            startActivity(startIntent);
                            finish();
                        } else {
                            Toast.makeText(this, "Error saving group", Toast.LENGTH_LONG).show();
                            if (groupResponse != null && groupResponse.getMessage() != null) {
                                Toast.makeText(this, groupResponse.getMessage(), Toast.LENGTH_LONG).show();
                            }
                        }
                    });
                } catch (Exception e) {
                    handler.post(() -> Toast.makeText(this, "Error saving: " + e.getMessage(), Toast.LENGTH_LONG).show());
                }
            });
        });

        btnCancel.setOnClickListener(view -> {
            finish();
        });
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (executor != null && !executor.isShutdown()) {
            executor.shutdownNow(); // Or executor.shutdown() depending on your needs
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