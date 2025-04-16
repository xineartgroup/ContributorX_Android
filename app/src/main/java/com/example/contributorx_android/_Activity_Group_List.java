package com.example.contributorx_android;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ListView;
import android.widget.Toast;
import androidx.activity.EdgeToEdge;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import java.util.List;

public class _Activity_Group_List extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_group_list);

        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        Button btnAddGroup = findViewById(R.id.btnAddGroup);
        ListView lstDetail = findViewById(R.id.lstDetail);

        if (_Activity_Login.LoggedOnUser == null){
            Toast.makeText(this, "Please log in first", Toast.LENGTH_LONG).show();
            Intent startIntent = new Intent(getApplicationContext(), _Activity_Login.class);
            startActivity(startIntent);
            finish();
            return;
        }

        if (_Activity_Login.LoggedOnUser.getRole().equals("Administrator")) {
            btnAddGroup.setVisibility(View.VISIBLE);
        }
        else{
            btnAddGroup.setVisibility(View.GONE);
        }

        List<Group> groups = _DAO_Group.GetAllGroups();

        _Layout_Group_List iAdapter = new _Layout_Group_List(this, groups);
        lstDetail.setAdapter(iAdapter);

        lstDetail.setOnItemClickListener((adapterView, view, i, l) -> {
            if ("Administrator".equals(_Activity_Login.LoggedOnUser.getRole())) {
                Group group = groups.get(i);
                if (group != null) {
                    Intent startIntent = new Intent(getApplicationContext(), _Activity_Group_Detail.class);
                    startIntent.putExtra("com.example.contributorx_android.ITEMINDEX", group.getId());
                    startActivity(startIntent);
                }
            }
        });

        btnAddGroup.setOnClickListener(view -> {
            Intent startIntent = new Intent(getApplicationContext(), _Activity_Group_Detail.class);
            startActivity(startIntent);
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