package com.example.contributorx_android;

import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.Button;
import android.widget.ListView;
import android.widget.SearchView;
import android.Manifest;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class _Activity_Contributor_List extends AppCompatActivity {

    List<Contributor> contributors = new ArrayList<>();
    _Layout_Contributor_List iAdapter = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_contributor_list);

        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        //flat_numbers = getResources().getStringArray(R.array.flat_number);

        if (ContextCompat.checkSelfPermission(this, Manifest.permission.READ_MEDIA_IMAGES) != PackageManager.PERMISSION_GRANTED) {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
                ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.READ_MEDIA_IMAGES}, 1);
            }
        }

        Button btnAddContributor = findViewById(R.id.btnAddContributor);
        ListView lstDetail = findViewById(R.id.lstDetail);
        SearchView searchView = findViewById(R.id.searchView);

        if (APIClass.LoggedOnUser == null){
            Intent startIntent = new Intent(getApplicationContext(), _Activity_Login.class);
            startActivity(startIntent);
            finish();
        }

        ExecutorService executor = Executors.newSingleThreadExecutor();
        Handler handler = new Handler(Looper.getMainLooper());

        executor.execute(() -> {
            APIResponse response = _DAO_Contributor.GetContributorsInCommunity(APIClass.LoggedOnUser.getCommunityId());

            handler.post(() -> {
                if (response.getIsSuccess()) {

                    contributors = response.getContributors();

                    iAdapter = new _Layout_Contributor_List(this, contributors);
                    lstDetail.setAdapter(iAdapter);

                    if (!"".equals(iAdapter.error)) {
                        Toast.makeText(this, iAdapter.error, Toast.LENGTH_SHORT).show();
                    }

                }
            });
        });

        lstDetail.setOnItemClickListener((adapterView, view, i, l) -> {
            if ("Administrator".equals(APIClass.LoggedOnUser.getRole())) {
                Contributor contributor = contributors.get(i);
                if (contributor != null) {
                    Intent startIntent = new Intent(getApplicationContext(), _Activity_Contributor_Detail.class);
                    startIntent.putExtra("com.example.contributorx_android.ITEMINDEX", contributor.getId());
                    startActivity(startIntent);
                }
            }
        });

        btnAddContributor.setOnClickListener(view -> {
            Intent startIntent = new Intent(getApplicationContext(), _Activity_Contributor_Detail.class);
            startActivity(startIntent);
        });

        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                // Optional: handle action on submit, but not needed for filtering
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                // Filter contributors and update adapter data
                iAdapter.contributors = Contributors(contributors, newText.trim());
                iAdapter.notifyDataSetChanged(); // Very important to refresh the view
                return true;
            }
        });
    }

    private List<Contributor> Contributors(List<Contributor> contributors, String query) {
        List<Contributor> result = new ArrayList<>();

        if (query.isEmpty()) {
            result.addAll(contributors);
        } else {
            query = query.toLowerCase();
            for (Contributor contributor : contributors) {
                // Filter by contributor username or firstname or surname
                if (contributor.getUserName().toLowerCase().contains(query) ||
                        contributor.getFirstname().toLowerCase().contains(query) ||
                        contributor.getLastname().toLowerCase().contains(query) ||
                        contributor.getEmail().toLowerCase().contains(query) ||
                        contributor.getPhoneNumber().toLowerCase().contains(query)) {
                    result.add(contributor);
                }
            }
        }

        return result;
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);

        if (requestCode == 1) {
            if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                Toast.makeText(this, "Permission granted!", Toast.LENGTH_SHORT).show();
            } else {
                Toast.makeText(this, "Permission denied. Cannot load images.", Toast.LENGTH_LONG).show();
            }
        }
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