package com.example.contributorx_android;

import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Build;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ListView;
import android.widget.Spinner;
import android.Manifest;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import java.util.ArrayList;
import java.util.List;

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
        Spinner cboStatus = findViewById(R.id.cboStatus);
        ListView lstDetail = findViewById(R.id.lstDetail);

        if (_Activity_Login.LoggedOnUser == null){
            Intent startIntent = new Intent(getApplicationContext(), _Activity_Login.class);
            startActivity(startIntent);
            finish();
        }

        contributors = _DAO_Contributor.GetContributorsInCommunity(_Activity_Login.LoggedOnUser.getId());

        iAdapter = new _Layout_Contributor_List(this, contributors);
        lstDetail.setAdapter(iAdapter);

        if (!"".equals(iAdapter.error)) {
            Toast.makeText(this, iAdapter.error, Toast.LENGTH_SHORT).show();
        }

        lstDetail.setOnItemClickListener((adapterView, view, i, l) -> {
            if ("Administrator".equals(_Activity_Login.LoggedOnUser.getRole())) {
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

        cboStatus.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {

                if (iAdapter != null) {
                    if (i == 0) {
                        iAdapter.contributors = _DAO_Contributor.GetAllContributors();
                        lstDetail.setAdapter(iAdapter);
                    }
                    else if (i == 1) {
                        iAdapter.contributors = _DAO_Contributor.GetActive();
                        lstDetail.setAdapter(iAdapter);
                    }
                    else if (i == 2) {
                        iAdapter.contributors = _DAO_Contributor.GetInActive();
                        lstDetail.setAdapter(iAdapter);
                    }
                }

            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });
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